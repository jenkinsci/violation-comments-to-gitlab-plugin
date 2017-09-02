package org.jenkinsci.plugins.jvctgl.perform;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.logging.Level.SEVERE;
import static org.gitlab.api.AuthMethod.HEADER;
import static org.gitlab.api.AuthMethod.URL_PARAMETER;
import static org.gitlab.api.TokenType.ACCESS_TOKEN;
import static org.gitlab.api.TokenType.PRIVATE_TOKEN;
import static org.jenkinsci.plugins.jvctgl.config.CredentialsHelper.findApiTokenCredentials;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_APITOKEN;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_APITOKENCREDENTIALSID;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_APITOKENPRIVATE;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_AUTHMETHODHEADER;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_COMMENTONLYCHANGEDCONTENT;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_CREATECOMMENTWITHALLSINGLEFILECOMMENTS;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_GITLABURL;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_IGNORECERTIFICATEERRORS;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_KEEP_OLD_COMMENTS;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_MERGEREQUESTID;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_MINSEVERITY;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_PROJECTID;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_SHOULD_SET_WIP;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_USEAPITOKEN;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_USEAPITOKENCREDENTIALS;
import static se.bjurr.violations.comments.gitlab.lib.ViolationCommentsToGitLabApi.violationCommentsToGitLabApi;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.parsers.FindbugsParser.setFindbugsMessagesXml;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.FilePath.FileCallable;
import hudson.model.TaskListener;
import hudson.model.Run;
import hudson.remoting.VirtualChannel;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Logger;

import org.gitlab.api.AuthMethod;
import org.gitlab.api.TokenType;
import org.jenkinsci.plugins.jvctgl.config.ViolationConfig;
import org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfig;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.jenkinsci.remoting.RoleChecker;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.util.Filtering;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.io.CharStreams;

public class JvctglPerformer {
  private static Logger LOG = Logger.getLogger(JvctglPerformer.class.getSimpleName());

  @VisibleForTesting
  public static void doPerform(
      final ViolationsToGitLabConfig config, final File workspace, final TaskListener listener)
      throws MalformedURLException {
    if (config.getMergeRequestId() == null) {
      listener
          .getLogger()
          .println("No merge request id defined, will not send violation comments.");
      return;
    }

    final List<Violation> allParsedViolations = newArrayList();
    for (final ViolationConfig violationConfig : config.getViolationConfigs()) {
      if (!isNullOrEmpty(violationConfig.getPattern())) {
        List<Violation> parsedViolations =
            violationsReporterApi() //
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

    final String apiToken =
        checkNotNull(emptyToNull(config.getApiToken()), "APIToken selected but not set!");
    final String hostUrl = config.getGitLabUrl();
    final String projectId = config.getProjectId();
    final String mergeRequestId = config.getMergeRequestId();

    listener
        .getLogger()
        .println("Will comment PR " + hostUrl + " " + projectId + " " + mergeRequestId);

    try {
      final TokenType tokenType = config.getApiTokenPrivate() ? PRIVATE_TOKEN : ACCESS_TOKEN;
      final AuthMethod authMethod = config.getAuthMethodHeader() ? HEADER : URL_PARAMETER;
      final Integer mergeRequestIdInteger = Integer.parseInt(mergeRequestId);
      final boolean shouldKeepOldComments = config.getKeepOldComments();
      final boolean shouldSetWIP = config.getShouldSetWip();
      violationCommentsToGitLabApi() //
          .setHostUrl(hostUrl) //
          .setProjectId(projectId) //
          .setMergeRequestId(mergeRequestIdInteger) //
          .setApiToken(apiToken) //
          .setTokenType(tokenType) //
          .setMethod(authMethod) //
          .setCommentOnlyChangedContent(config.getCommentOnlyChangedContent()) //
          .setCreateCommentWithAllSingleFileComments(
              config.getCreateCommentWithAllSingleFileComments()) //
          /**
           * Cannot yet support this because the API does not support it.
           * https://gitlab.com/gitlab-org/gitlab-ce/issues/14850
           */
          .setIgnoreCertificateErrors(config.getIgnoreCertificateErrors()) //
          .setViolations(allParsedViolations) //
          .setShouldKeepOldComments(shouldKeepOldComments) //
          .setShouldSetWIP(shouldSetWIP) //
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
    expanded.setMergeRequestId(environment.expand(config.getMergeRequestId()));

    expanded.setUseApiToken(config.getUseApiToken());
    expanded.setApiToken(config.getApiToken());

    expanded.setUseApiTokenCredentials(config.isUseApiTokenCredentials());
    expanded.setApiTokenCredentialsId(config.getApiTokenCredentialsId());

    expanded.setAuthMethodHeader(config.getAuthMethodHeader());
    expanded.setApiTokenPrivate(config.getApiTokenPrivate());
    expanded.setIgnoreCertificateErrors(config.getIgnoreCertificateErrors());

    expanded.setCommentOnlyChangedContent(config.getCommentOnlyChangedContent());
    expanded.setCreateCommentWithAllSingleFileComments(
        config.getCreateCommentWithAllSingleFileComments());
    expanded.setMinSeverity(config.getMinSeverity());
    expanded.setShouldSetWip(config.getShouldSetWip());
    expanded.setKeepOldComments(config.getKeepOldComments());

    for (final ViolationConfig violationConfig : config.getViolationConfigs()) {
      final String pattern = environment.expand(violationConfig.getPattern());
      final String reporter = violationConfig.getReporter();
      final Parser parser = violationConfig.getParser();
      if (isNullOrEmpty(pattern) || isNullOrEmpty(reporter) || parser == null) {
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

      setApiTokenCredentials(configExpanded, listener);

      listener.getLogger().println("Running Violation Comments To GitLab");
      listener.getLogger().println("Merge request: " + configExpanded.getMergeRequestId());

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
              doPerform(configExpanded, workspace, listener);
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
    logger.println(FIELD_MERGEREQUESTID + ": " + config.getMergeRequestId());

    logger.println(FIELD_USEAPITOKEN + ": " + config.getUseApiToken());
    logger.println(FIELD_APITOKEN + ": " + !isNullOrEmpty(config.getApiToken()));

    logger.println(FIELD_USEAPITOKENCREDENTIALS + ": " + config.isUseApiTokenCredentials());
    logger.println(
        FIELD_APITOKENCREDENTIALSID + ": " + !isNullOrEmpty(config.getApiTokenCredentialsId()));
    logger.println(FIELD_IGNORECERTIFICATEERRORS + ": " + config.getIgnoreCertificateErrors());
    logger.println(FIELD_APITOKENPRIVATE + ": " + config.getApiTokenPrivate());
    logger.println(FIELD_AUTHMETHODHEADER + ": " + config.getAuthMethodHeader());

    logger.println(
        FIELD_CREATECOMMENTWITHALLSINGLEFILECOMMENTS
            + ": "
            + config.getCreateCommentWithAllSingleFileComments());
    logger.println(FIELD_COMMENTONLYCHANGEDCONTENT + ": " + config.getCommentOnlyChangedContent());

    logger.println(FIELD_MINSEVERITY + ": " + config.getMinSeverity());
    logger.println(FIELD_KEEP_OLD_COMMENTS + ": " + config.getKeepOldComments());
    logger.println(FIELD_SHOULD_SET_WIP + ": " + config.getShouldSetWip());

    for (final ViolationConfig violationConfig : config.getViolationConfigs()) {
      logger.println(violationConfig.getParser() + " with pattern " + violationConfig.getPattern());
    }
  }

  private static void setApiTokenCredentials(
      final ViolationsToGitLabConfig configExpanded, final TaskListener listener) {
    if (configExpanded.isUseApiTokenCredentials()) {
      final String getoApiTokenCredentialsId = configExpanded.getApiTokenCredentialsId();
      if (!isNullOrEmpty(getoApiTokenCredentialsId)) {
        final Optional<StringCredentials> credentials =
            findApiTokenCredentials(getoApiTokenCredentialsId);
        if (credentials.isPresent()) {
          final StringCredentials stringCredential =
              checkNotNull(credentials.get(), "Credentials API token selected but not set!");
          configExpanded.setApiToken(stringCredential.getSecret().getPlainText());
          listener.getLogger().println("Using API token from credentials");
        } else {
          listener.getLogger().println("API token credentials not found!");
          return;
        }
      } else {
        listener.getLogger().println("API token credentials checked but not selected!");
        return;
      }
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
