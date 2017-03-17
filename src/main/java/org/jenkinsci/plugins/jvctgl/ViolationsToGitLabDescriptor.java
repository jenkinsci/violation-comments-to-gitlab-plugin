package org.jenkinsci.plugins.jvctgl;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_APITOKEN;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_APITOKENCREDENTIALSID;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_APITOKENPRIVATE;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_AUTHMETHODHEADER;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_COMMENTONLYCHANGEDCONTENT;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_CREATECOMMENTWITHALLSINGLEFILECOMMENTS;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_GITLABURL;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_IGNORECERTIFICATEERRORS;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_MERGEREQUESTID;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_MINSEVERITY;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_PATTERN;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_PROJECTID;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_USEAPITOKEN;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_USEAPITOKENCREDENTIALS;
import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.createNewConfig;

import java.util.List;

import org.jenkinsci.plugins.jvctgl.config.CredentialsHelper;
import org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfig;
import org.kohsuke.stapler.StaplerRequest;

import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import hudson.util.ListBoxModel;
import net.sf.json.JSONObject;
import se.bjurr.violations.lib.model.SEVERITY;

public final class ViolationsToGitLabDescriptor extends BuildStepDescriptor<Publisher> {
  private ViolationsToGitLabConfig config;

  public ViolationsToGitLabDescriptor() {
    super(ViolationsToGitLabRecorder.class);
    load();
    if (this.config == null
        || this.config.getViolationConfigs().size()
            != createNewConfig().getViolationConfigs().size()) {
      this.config = createNewConfig();
    }
  }

  public ListBoxModel doFillApiTokenCredentialsIdItems() {
    return CredentialsHelper.doFillApiTokenCredentialsIdItems();
  }

  @Override
  public String getDisplayName() {
    return "Report Violations to GitLab";
  }

  @Override
  public String getHelpFile() {
    return super.getHelpFile();
  }

  /** Create new blank configuration. Used when job is created. */
  public ViolationsToGitLabConfig getNewConfig() {
    return createNewConfig();
  }

  @Override
  public boolean isApplicable(
      @SuppressWarnings("rawtypes") final Class<? extends AbstractProject> jobType) {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Publisher newInstance(StaplerRequest req, JSONObject formData)
      throws hudson.model.Descriptor.FormException {
    ViolationsToGitLabConfig config = createNewConfig();
    config.setGitLabUrl(formData.getString(FIELD_GITLABURL));
    config.setProjectId(formData.getString(FIELD_PROJECTID));
    config.setMergeRequestId(formData.getString(FIELD_MERGEREQUESTID));

    config.setCreateCommentWithAllSingleFileComments(
        formData.getString(FIELD_CREATECOMMENTWITHALLSINGLEFILECOMMENTS).equalsIgnoreCase("true"));
    config.setCommentOnlyChangedContent(
        formData.getString(FIELD_COMMENTONLYCHANGEDCONTENT).equalsIgnoreCase("true"));

    config.setUseApiToken(formData.getBoolean(FIELD_USEAPITOKEN));
    config.setApiToken(formData.getString(FIELD_APITOKEN));

    config.setUseApiTokenCredentials(
        formData.getString(FIELD_USEAPITOKENCREDENTIALS).equalsIgnoreCase("true"));
    config.setApiTokenCredentialsId(formData.getString(FIELD_APITOKENCREDENTIALSID));

    config.setAuthMethodHeader(formData.getString(FIELD_AUTHMETHODHEADER).equalsIgnoreCase("true"));
    config.setApiTokenPrivate(formData.getBoolean(FIELD_APITOKENPRIVATE));
    config.setIgnoreCertificateErrors(
        formData.getString(FIELD_IGNORECERTIFICATEERRORS).equalsIgnoreCase("true"));

    String minSeverityString = formData.getString(FIELD_MINSEVERITY);
    if (!isNullOrEmpty(minSeverityString)) {
      config.setMinSeverity(SEVERITY.valueOf(minSeverityString));
    } else {
      config.setMinSeverity(null);
    }

    int i = 0;
    for (String pattern : (List<String>) formData.get(FIELD_PATTERN)) {
      config.getViolationConfigs().get(i++).setPattern(pattern);
    }
    ViolationsToGitLabRecorder publisher = new ViolationsToGitLabRecorder();
    publisher.setConfig(config);
    return publisher;
  }
}
