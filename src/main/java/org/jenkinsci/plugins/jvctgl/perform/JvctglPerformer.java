package org.jenkinsci.plugins.jvctgl.perform;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.logging.Level.SEVERE;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_APITOKEN;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_APITOKENCREDENTIALSID;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_APITOKENPRIVATE;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_AUTHMETHODHEADER;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_COMMENTONLYCHANGEDCONTENT;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_CREATECOMMENTWITHALLSINGLEFILECOMMENTS;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_GITLABURL;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_IGNORECERTIFICATEERRORS;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_KEEP_OLD_COMMENTS;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_MERGEREQUESTIID;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_MINSEVERITY;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_PROJECTID;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_SHOULD_SET_WIP;
import static se.bjurr.violations.comments.gitlab.lib.ViolationCommentsToGitLabApi.violationCommentsToGitLabApi;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.parsers.FindbugsParser.setFindbugsMessagesXml;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.io.CharStreams;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.FilePath.FileCallable;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.remoting.VirtualChannel;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gitlab4j.api.Constants.TokenType;
import org.jenkinsci.plugins.jvctgl.config.CredentialsHelper;
import org.jenkinsci.plugins.jvctgl.config.ViolationConfig;
import org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfig;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.jenkinsci.remoting.RoleChecker;
import se.bjurr.violations.comments.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.util.Filtering;

public class JvctglPerformer {
  private static Logger LOG = Logger.getLogger(JvctglPerformer.class.getSimpleName());

  @VisibleForTesting
  public static void doPerform(
      final ViolationsToGitLabConfig config,
      final Optional<StringCredentials> apiTokenCredentials,
      final File workspace,
      final TaskListener listener)
      throws MalformedURLException {
    if (config.getMergeRequestIid() == null) {
      listener
          .getLogger()
          .println(
              "No merge request id defined, will not send violation comments. "
                  + "\n\nPossible cause might be: mergeRequestId changed in version 2.0 and is now mergeRequestIid. "
                  + "If you just updated the plugin, mergeRequestIid should have the same value that you previously used for mergeRequestId. "
                  + "It is named wrong in the Java GitLab API and that wrong name has spread to this API."
                  + "\n\n");
      return;
    }

    final List<Violation> allParsedViolations = newArrayList();
    for (final ViolationConfig violationConfig : config.getViolationConfigs()) {
      if (!isNullOrEmpty(violationConfig.getPattern())) {
        List<Violation> parsedViolations =
            violationsApi() //
                .findAll(violationConfig.getParser()) //
                .withReporter(violationConfig.getReporter()) //
                .inFolder(workspace.getAbsolutePath()) //
                .withPattern(violationConfig.getPattern()) //
                .violations();
        final SEVERITY minSeverity = config.getMinSeverity();
        if (minSeverity != null) {
          parsedViolations = Filtering.withAtLEastSeverity(parsedViolations, minSeverity);
        }

        allParsedViolations.addAll(parsedViolations);
        listener
            .getLogger()
            .println(
                "Found " + parsedViolations.size() + " violations from " + violationConfig + ".");
      }
    }

    String apiToken = config.getApiToken();
    if (apiTokenCredentials.isPresent()) {
      apiToken = apiTokenCredentials.get().getSecret().getPlainText();
    }
    if (isNullOrEmpty(apiToken)) {
      throw new IllegalStateException("No credentials found!");
    }

    final String hostUrl = config.getGitLabUrl();
    final String projectId = config.getProjectId();
    final String mergeRequestIid = config.getMergeRequestIid();

    listener
        .getLogger()
        .println("Will comment PR " + hostUrl + " " + projectId + " " + mergeRequestIid);

    try {
      final TokenType tokenType =
          config.getApiTokenPrivate() ? TokenType.PRIVATE : TokenType.ACCESS;
      final Integer mergeRequestIidInteger = Integer.parseInt(mergeRequestIid);
      final boolean shouldKeepOldComments = config.getKeepOldComments();
      final boolean shouldSetWIP = config.getShouldSetWip();
      final String commentTemplate = config.getCommentTemplate();
      violationCommentsToGitLabApi() //
          .setProxyServer(config.getProxyUri()) //
          .setProxyUser(config.getProxyUser()) //
          .setProxyPassword(config.getProxyPassword()) //
          .setHostUrl(hostUrl) //
          .setProjectId(projectId) //
          .setMergeRequestIid(mergeRequestIidInteger) //
          .setApiToken(apiToken) //
          .setTokenType(tokenType) //
          .setCommentOnlyChangedContent(config.getCommentOnlyChangedContent()) //
          .setCreateCommentWithAllSingleFileComments(
              config.getCreateCommentWithAllSingleFileComments()) //
          .setCreateSingleFileComments(config.getCreateSingleFileComments())
          .setIgnoreCertificateErrors(config.getIgnoreCertificateErrors()) //
          .setViolations(allParsedViolations) //
          .setShouldKeepOldComments(shouldKeepOldComments) //
          .setShouldSetWIP(shouldSetWIP) //
          .setCommentTemplate(commentTemplate) //
          .setViolationsLogger(
              new ViolationsLogger() {
                @Override
                public void log(final Level level, final String string) {
                  listener.getLogger().println(level + " " + string);
                }

                @Override
                public void log(final Level level, final String string, final Throwable t) {
                  final StringWriter sw = new StringWriter();
                  t.printStackTrace(new PrintWriter(sw));
                  listener.getLogger().println(level + " " + sw.toString());
                }
              }) //
          .toPullRequest();
    } catch (final Exception e) {
      Logger.getLogger(JvctglPerformer.class.getName()).log(SEVERE, "", e);
      final StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      listener.getLogger().println(sw.toString());
    }
  }

  /** Makes sure any Jenkins variable, used in the configuration fields, are evaluated. */
  @VisibleForTesting
  static ViolationsToGitLabConfig expand(
      final ViolationsToGitLabConfig config, final EnvVars environment) {
    final ViolationsToGitLabConfig expanded = new ViolationsToGitLabConfig();
    expanded.setGitLabUrl(environment.expand(config.getGitLabUrl()));
    expanded.setProjectId(environment.expand(config.getProjectId()));
    expanded.setMergeRequestIid(environment.expand(config.getMergeRequestIid()));

    expanded.setApiToken(config.getApiToken());

    expanded.setApiTokenCredentialsId(config.getApiTokenCredentialsId());

    expanded.setAuthMethodHeader(config.getAuthMethodHeader());
    expanded.setApiTokenPrivate(config.getApiTokenPrivate());
    expanded.setIgnoreCertificateErrors(config.getIgnoreCertificateErrors());

    expanded.setCommentOnlyChangedContent(config.getCommentOnlyChangedContent());
    expanded.setCreateCommentWithAllSingleFileComments(
        config.getCreateCommentWithAllSingleFileComments());
    expanded.setCreateSingleFileComments(config.getCreateSingleFileComments());
    expanded.setMinSeverity(config.getMinSeverity());
    expanded.setShouldSetWip(config.getShouldSetWip());
    expanded.setKeepOldComments(config.getKeepOldComments());
    expanded.setCommentTemplate(config.getCommentTemplate());

    expanded.setProxyUri(config.getProxyUri());
    expanded.setProxyUser(config.getProxyUser());
    expanded.setProxyPassword(config.getProxyPassword());

    for (final ViolationConfig violationConfig : config.getViolationConfigs()) {
      final String pattern = environment.expand(violationConfig.getPattern());
      final String reporter = violationConfig.getReporter();
      final Parser parser = violationConfig.getParser();
      if (isNullOrEmpty(pattern) || parser == null) {
        LOG.fine("Ignoring violationConfig because of null/empty -values: " + violationConfig);
        continue;
      }
      final ViolationConfig p = new ViolationConfig();
      p.setPattern(pattern);
      p.setReporter(reporter);
      p.setParser(parser);
      expanded.getViolationConfigs().add(p);
    }
    return expanded;
  }

  public static void jvctsPerform(
      final ViolationsToGitLabConfig configUnexpanded,
      final FilePath fp,
      final Run<?, ?> build,
      final TaskListener listener) {
    try {
      final EnvVars env = build.getEnvironment(listener);
      final ViolationsToGitLabConfig configExpanded = expand(configUnexpanded, env);
      listener.getLogger().println("---");
      listener.getLogger().println("--- Violation Comments to GitLab ---");
      listener.getLogger().println("---");
      logConfiguration(configExpanded, build, listener);

      final Optional<StringCredentials> apiTokenCredentials =
          CredentialsHelper.findApiTokenCredentials(
              build.getParent(),
              configExpanded.getApiTokenCredentialsId(),
              configExpanded.getGitLabUrl());

      listener.getLogger().println("Running Violation Comments To GitLab");
      listener.getLogger().println("Merge request: " + configExpanded.getMergeRequestIid());

      fp.act(
          new FileCallable<Void>() {

            private static final long serialVersionUID = -7686563245942529513L;

            @Override
            public void checkRoles(final RoleChecker checker) throws SecurityException {}

            @Override
            public Void invoke(final File workspace, final VirtualChannel channel)
                throws IOException, InterruptedException {
              setupFindBugsMessages();
              listener.getLogger().println("Workspace: " + workspace.getAbsolutePath());
              doPerform(configExpanded, apiTokenCredentials, workspace, listener);
              return null;
            }
          });
    } catch (final Exception e) {
      Logger.getLogger(JvctglPerformer.class.getName()).log(SEVERE, "", e);
      final StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      listener.getLogger().println(sw.toString());
      return;
    }
  }

  private static void logConfiguration(
      final ViolationsToGitLabConfig config, final Run<?, ?> build, final TaskListener listener) {
    final PrintStream logger = listener.getLogger();
    logger.println(FIELD_GITLABURL + ": " + config.getGitLabUrl());
    logger.println(FIELD_PROJECTID + ": " + config.getProjectId());
    logger.println(FIELD_MERGEREQUESTIID + ": " + config.getMergeRequestIid());

    logger.println(FIELD_APITOKEN + ": " + !isNullOrEmpty(config.getApiToken()));

    logger.println(
        FIELD_APITOKENCREDENTIALSID + ": " + !isNullOrEmpty(config.getApiTokenCredentialsId()));
    logger.println(FIELD_IGNORECERTIFICATEERRORS + ": " + config.getIgnoreCertificateErrors());
    logger.println(FIELD_APITOKENPRIVATE + ": " + config.getApiTokenPrivate());
    logger.println(FIELD_AUTHMETHODHEADER + ": " + config.getAuthMethodHeader());

    logger.println(
        FIELD_CREATECOMMENTWITHALLSINGLEFILECOMMENTS
            + ": "
            + config.getCreateCommentWithAllSingleFileComments());
    logger.println("createSingleFileComments" + ": " + config.getCreateSingleFileComments());
    logger.println(FIELD_COMMENTONLYCHANGEDCONTENT + ": " + config.getCommentOnlyChangedContent());

    logger.println(FIELD_MINSEVERITY + ": " + config.getMinSeverity());
    logger.println(FIELD_KEEP_OLD_COMMENTS + ": " + config.getKeepOldComments());
    logger.println(FIELD_SHOULD_SET_WIP + ": " + config.getShouldSetWip());
    logger.println("commentTemplate: " + config.getCommentTemplate());
    logger.println("proxyUri: " + config.getProxyUri());
    logger.println("proxyUser: " + (nullToEmpty(config.getProxyUser()).isEmpty() ? "no" : "yes"));
    logger.println(
        "proxyPassword: " + (nullToEmpty(config.getProxyPassword()).isEmpty() ? "no" : "yes"));

    for (final ViolationConfig violationConfig : config.getViolationConfigs()) {
      logger.println(violationConfig.getParser() + " with pattern " + violationConfig.getPattern());
    }
  }

  private static void setupFindBugsMessages() {
    try {
      final String findbugsMessagesXml =
          CharStreams.toString(
              new InputStreamReader(
                  JvctglPerformer.class.getResourceAsStream("findbugs-messages.xml"), UTF_8));
      setFindbugsMessagesXml(findbugsMessagesXml);
    } catch (final IOException e) {
      propagate(e);
    }
  }
}
