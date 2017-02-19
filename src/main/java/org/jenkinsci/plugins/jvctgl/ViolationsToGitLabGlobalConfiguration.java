package org.jenkinsci.plugins.jvctgl;

import java.io.Serializable;

import org.jenkinsci.plugins.jvctgl.config.CredentialsHelper;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.util.ListBoxModel;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;

@Extension
public class ViolationsToGitLabGlobalConfiguration extends GlobalConfiguration
    implements Serializable {

  private static final long serialVersionUID = -7071307231169224261L;

  /**
   * Returns this singleton instance.
   *
   * @return the singleton.
   */
  public static ViolationsToGitLabGlobalConfiguration get() {
    return GlobalConfiguration.all().get(ViolationsToGitLabGlobalConfiguration.class);
  }

  private String gitLabUrl;

  private Boolean ignoreCertificateErrors;

  private String apiToken;
  private Boolean apiTokenPrivate;
  private Boolean authMethodHeader;
  private String apiTokenCredentialsId;

  public ViolationsToGitLabGlobalConfiguration() {
    load();
  }

  @Override
  public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
    req.bindJSON(this, json);
    save();
    return true;
  }

  public ListBoxModel doFillApiTokenCredentialsIdItems() {
    return CredentialsHelper.doFillApiTokenCredentialsIdItems();
  }

  public String getGitLabUrl() {
    return gitLabUrl;
  }

  public Boolean isIgnoreCertificateErrors() {
    return ignoreCertificateErrors;
  }

  @DataBoundSetter
  public void setIgnoreCertificateErrors(boolean ignoreCertificateErrors) {
    this.ignoreCertificateErrors = ignoreCertificateErrors;
  }

  public String getApiToken() {
    return apiToken;
  }

  @DataBoundSetter
  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }

  public String getApiTokenCredentialsId() {
    return apiTokenCredentialsId;
  }

  @DataBoundSetter
  public void setApiTokenCredentialsId(String apiTokenCredentialsId) {
    this.apiTokenCredentialsId = apiTokenCredentialsId;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @DataBoundSetter
  public void setGitLabUrl(String gitLabUrl) {
    this.gitLabUrl = gitLabUrl;
  }

  public Boolean isApiTokenPrivate() {
    return apiTokenPrivate;
  }

  @DataBoundSetter
  public void setApiTokenPrivate(boolean apiTokenPrivate) {
    this.apiTokenPrivate = apiTokenPrivate;
  }

  public Boolean isAuthMethodHeader() {
    return authMethodHeader;
  }

  @DataBoundSetter
  public void setAuthMethodHeader(boolean authMethodHeader) {
    this.authMethodHeader = authMethodHeader;
  }

  @Override
  public String toString() {
    return "ViolationsToGitLabGlobalConfiguration [gitLabUrl="
        + gitLabUrl
        + ", ignoreCertificateErrors="
        + ignoreCertificateErrors
        + ", apiToken="
        + apiToken
        + ", apiTokenPrivate="
        + apiTokenPrivate
        + ", authMethodHeader="
        + authMethodHeader
        + ", apiTokenCredentialsId="
        + apiTokenCredentialsId
        + "]";
  }
}
