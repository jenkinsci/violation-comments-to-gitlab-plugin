package org.jenkinsci.plugins.jvctgl;

import hudson.Extension;
import hudson.model.Item;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import java.io.Serializable;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.jenkinsci.plugins.jvctgl.config.CredentialsHelper;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import se.bjurr.violations.lib.model.SEVERITY;

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

  private boolean ignoreCertificateErrors;

  private String apiToken;
  private boolean apiTokenPrivate;
  private boolean authMethodHeader;
  private String apiTokenCredentialsId;
  private SEVERITY minSeverity = SEVERITY.INFO;

  public ViolationsToGitLabGlobalConfiguration() {
    load();
  }

  @Override
  public boolean configure(final StaplerRequest req, final JSONObject json) throws FormException {
    req.bindJSON(this, json);
    save();
    return true;
  }

  @Restricted(NoExternalUse.class)
  public ListBoxModel doFillMinSeverityItems() {
    final ListBoxModel items = new ListBoxModel();
    for (final SEVERITY severity : SEVERITY.values()) {
      items.add(severity.name());
    }
    return items;
  }

  @SuppressWarnings("unused") // Used by stapler
  public ListBoxModel doFillApiTokenCredentialsIdItems(
      @AncestorInPath Item item,
      @QueryParameter String apiTokenCredentialsId,
      @QueryParameter String gitLabUrl) {
    return CredentialsHelper.doFillApiTokenCredentialsIdItems(
        item, apiTokenCredentialsId, gitLabUrl);
  }

  @SuppressWarnings("unused") // Used by stapler
  public FormValidation doCheckApiTokenCredentialsId(
      @AncestorInPath Item item, @QueryParameter String value, @QueryParameter String gitLabUrl) {
    return CredentialsHelper.doCheckApiTokenCredentialsId(item, value, gitLabUrl);
  }

  public String getGitLabUrl() {
    return gitLabUrl;
  }

  public boolean isIgnoreCertificateErrors() {
    return ignoreCertificateErrors;
  }

  @DataBoundSetter
  public void setIgnoreCertificateErrors(final boolean ignoreCertificateErrors) {
    this.ignoreCertificateErrors = ignoreCertificateErrors;
  }

  public String getApiToken() {
    return apiToken;
  }

  @DataBoundSetter
  public void setApiToken(final String apiToken) {
    this.apiToken = apiToken;
  }

  public String getApiTokenCredentialsId() {
    return apiTokenCredentialsId;
  }

  @DataBoundSetter
  public void setMinSeverity(final SEVERITY minSeverity) {
    this.minSeverity = minSeverity;
  }

  public SEVERITY getMinSeverity() {
    return minSeverity;
  }

  @DataBoundSetter
  public void setApiTokenCredentialsId(final String apiTokenCredentialsId) {
    this.apiTokenCredentialsId = apiTokenCredentialsId;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @DataBoundSetter
  public void setGitLabUrl(final String gitLabUrl) {
    this.gitLabUrl = gitLabUrl;
  }

  public boolean isApiTokenPrivate() {
    return apiTokenPrivate;
  }

  @DataBoundSetter
  public void setApiTokenPrivate(final boolean apiTokenPrivate) {
    this.apiTokenPrivate = apiTokenPrivate;
  }

  public boolean isAuthMethodHeader() {
    return authMethodHeader;
  }

  @DataBoundSetter
  public void setAuthMethodHeader(final boolean authMethodHeader) {
    this.authMethodHeader = authMethodHeader;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (apiToken == null ? 0 : apiToken.hashCode());
    result =
        prime * result + (apiTokenCredentialsId == null ? 0 : apiTokenCredentialsId.hashCode());
    result = prime * result + (apiTokenPrivate ? 1231 : 1237);
    result = prime * result + (authMethodHeader ? 1231 : 1237);
    result = prime * result + (gitLabUrl == null ? 0 : gitLabUrl.hashCode());
    result = prime * result + (ignoreCertificateErrors ? 1231 : 1237);
    result = prime * result + (minSeverity == null ? 0 : minSeverity.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ViolationsToGitLabGlobalConfiguration other = (ViolationsToGitLabGlobalConfiguration) obj;
    if (apiToken == null) {
      if (other.apiToken != null) {
        return false;
      }
    } else if (!apiToken.equals(other.apiToken)) {
      return false;
    }
    if (apiTokenCredentialsId == null) {
      if (other.apiTokenCredentialsId != null) {
        return false;
      }
    } else if (!apiTokenCredentialsId.equals(other.apiTokenCredentialsId)) {
      return false;
    }
    if (apiTokenPrivate != other.apiTokenPrivate) {
      return false;
    }
    if (authMethodHeader != other.authMethodHeader) {
      return false;
    }
    if (gitLabUrl == null) {
      if (other.gitLabUrl != null) {
        return false;
      }
    } else if (!gitLabUrl.equals(other.gitLabUrl)) {
      return false;
    }
    if (ignoreCertificateErrors != other.ignoreCertificateErrors) {
      return false;
    }
    if (minSeverity != other.minSeverity) {
      return false;
    }
    return true;
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
        + ", minSeverity="
        + minSeverity
        + "]";
  }
}
