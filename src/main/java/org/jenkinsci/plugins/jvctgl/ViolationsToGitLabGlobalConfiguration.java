package org.jenkinsci.plugins.jvctgl;

import java.io.Serializable;

import org.jenkinsci.plugins.jvctgl.config.CredentialsHelper;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.util.ListBoxModel;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
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

  private Boolean ignoreCertificateErrors;

  private String apiToken;
  private Boolean apiTokenPrivate;
  private Boolean authMethodHeader;
  private String apiTokenCredentialsId;
  private SEVERITY minSeverity;

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
  public void setMinSeverity(SEVERITY minSeverity) {
    this.minSeverity = minSeverity;
  }

  public SEVERITY getMinSeverity() {
    return minSeverity;
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
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (apiToken == null ? 0 : apiToken.hashCode());
    result =
        prime * result + (apiTokenCredentialsId == null ? 0 : apiTokenCredentialsId.hashCode());
    result = prime * result + (apiTokenPrivate == null ? 0 : apiTokenPrivate.hashCode());
    result = prime * result + (authMethodHeader == null ? 0 : authMethodHeader.hashCode());
    result = prime * result + (gitLabUrl == null ? 0 : gitLabUrl.hashCode());
    result =
        prime * result + (ignoreCertificateErrors == null ? 0 : ignoreCertificateErrors.hashCode());
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
    ViolationsToGitLabGlobalConfiguration other = (ViolationsToGitLabGlobalConfiguration) obj;
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
    if (apiTokenPrivate == null) {
      if (other.apiTokenPrivate != null) {
        return false;
      }
    } else if (!apiTokenPrivate.equals(other.apiTokenPrivate)) {
      return false;
    }
    if (authMethodHeader == null) {
      if (other.authMethodHeader != null) {
        return false;
      }
    } else if (!authMethodHeader.equals(other.authMethodHeader)) {
      return false;
    }
    if (gitLabUrl == null) {
      if (other.gitLabUrl != null) {
        return false;
      }
    } else if (!gitLabUrl.equals(other.gitLabUrl)) {
      return false;
    }
    if (ignoreCertificateErrors == null) {
      if (other.ignoreCertificateErrors != null) {
        return false;
      }
    } else if (!ignoreCertificateErrors.equals(other.ignoreCertificateErrors)) {
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
