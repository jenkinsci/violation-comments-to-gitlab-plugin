package org.jenkinsci.plugins.jvctgl.config;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jenkinsci.plugins.jvctgl.ViolationsToGitLabGlobalConfiguration;
import org.kohsuke.stapler.DataBoundConstructor;

public class ViolationsToGitLabConfig implements Serializable {
  private static final long serialVersionUID = 4851568645021422528L;

  private boolean commentOnlyChangedContent;
  private boolean createCommentWithAllSingleFileComments;
  private List<ViolationConfig> violationConfigs;
  private String gitLabUrl;
  private String apiToken;
  private Boolean useApiToken;
  private String projectId;
  private String mergeRequestId;
  private Boolean useApiTokenCredentials;
  private String apiTokenCredentialsId;
  private Boolean ignoreCertificateErrors;
  private Boolean apiTokenPrivate;
  private Boolean authMethodHeader;

  @DataBoundConstructor
  public ViolationsToGitLabConfig(
      boolean commentOnlyChangedContent,
      boolean createCommentWithAllSingleFileComments,
      List<ViolationConfig> violationConfigs,
      String gitLabUrl,
      String apiToken,
      String projectId,
      String mergeRequestId,
      boolean useApiTokenCredentials,
      String apiTokenCredentialsId,
      boolean ignoreCertificateErrors,
      boolean apiTokenPrivate,
      boolean authMethodHeader,
      boolean useApiToken) {
    List<ViolationConfig> allViolationConfigs = includeAllReporters(violationConfigs);

    this.commentOnlyChangedContent = commentOnlyChangedContent;
    this.createCommentWithAllSingleFileComments = createCommentWithAllSingleFileComments;
    this.violationConfigs = allViolationConfigs;
    this.gitLabUrl = gitLabUrl;
    this.apiToken = apiToken;
    this.projectId = projectId;
    this.mergeRequestId = mergeRequestId;
    this.useApiTokenCredentials = useApiTokenCredentials;
    this.apiTokenCredentialsId = apiTokenCredentialsId;
    this.ignoreCertificateErrors = ignoreCertificateErrors;
    this.apiTokenPrivate = apiTokenPrivate;
    this.authMethodHeader = authMethodHeader;
    this.useApiToken = useApiToken;
  }

  public ViolationsToGitLabConfig(ViolationsToGitLabConfig rhs) {
    this.violationConfigs = rhs.violationConfigs;
    this.createCommentWithAllSingleFileComments = rhs.createCommentWithAllSingleFileComments;
    this.commentOnlyChangedContent = rhs.commentOnlyChangedContent;

    this.gitLabUrl = rhs.gitLabUrl;
    this.apiToken = rhs.apiToken;
    this.projectId = rhs.projectId;
    this.mergeRequestId = rhs.mergeRequestId;
    this.useApiTokenCredentials = rhs.useApiTokenCredentials;
    this.apiTokenCredentialsId = rhs.apiTokenCredentialsId;
    this.ignoreCertificateErrors = rhs.ignoreCertificateErrors;
    this.apiTokenPrivate = rhs.apiTokenPrivate;
    this.authMethodHeader = rhs.authMethodHeader;
    this.useApiToken = rhs.useApiToken;
  }

  public ViolationsToGitLabConfig() {
    this.violationConfigs = new ArrayList<>();
  }

  public void applyDefaults(ViolationsToGitLabGlobalConfiguration defaults) {
    if (defaults == null) {
      return;
    }

    if (isNullOrEmpty(this.gitLabUrl)) {
      this.gitLabUrl = defaults.getGitLabUrl();
    }
    if (isNullOrEmpty(this.apiToken)) {
      this.apiToken = defaults.getApiToken();
    }
    if (isNullOrEmpty(this.apiTokenCredentialsId)) {
      this.apiTokenCredentialsId = defaults.getApiTokenCredentialsId();
    }
    if (ignoreCertificateErrors == null) {
      this.ignoreCertificateErrors = defaults.isIgnoreCertificateErrors();
    }
    if (apiTokenPrivate == null) {
      this.apiTokenPrivate = defaults.isApiTokenPrivate();
    }
    if (authMethodHeader == null) {
      this.authMethodHeader = defaults.isAuthMethodHeader();
    }
  }

  public String getGitLabUrl() {
    return gitLabUrl;
  }

  public boolean getCommentOnlyChangedContent() {
    return this.commentOnlyChangedContent;
  }

  public boolean getCreateCommentWithAllSingleFileComments() {
    return this.createCommentWithAllSingleFileComments;
  }

  public List<ViolationConfig> getViolationConfigs() {
    return this.violationConfigs;
  }

  public void setIgnoreCertificateErrors(Boolean ignoreCertificateErrors) {
    this.ignoreCertificateErrors = ignoreCertificateErrors;
  }

  public void setApiTokenPrivate(Boolean apiTokenPrivate) {
    this.apiTokenPrivate = apiTokenPrivate;
  }

  public void setUseApiTokenCredentials(Boolean useApiTokenCredentials) {
    this.useApiTokenCredentials = useApiTokenCredentials;
  }

  public void setMergeRequestId(String mergeRequestId) {
    this.mergeRequestId = mergeRequestId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public void setApiTokenCredentialsId(String apiTokenCredentialsId) {
    this.apiTokenCredentialsId = apiTokenCredentialsId;
  }

  public void setAuthMethodHeader(Boolean authMethodHeader) {
    this.authMethodHeader = authMethodHeader;
  }

  private List<ViolationConfig> includeAllReporters(List<ViolationConfig> violationConfigs) {
    List<ViolationConfig> allViolationConfigs =
        ViolationsToGitLabConfigHelper.getAllViolationConfigs();
    for (ViolationConfig candidate : allViolationConfigs) {
      for (ViolationConfig input : violationConfigs) {
        if (candidate.getReporter() == input.getReporter()) {
          candidate.setPattern(input.getPattern());
        }
      }
    }
    return allViolationConfigs;
  }

  public String getApiToken() {
    return apiToken;
  }

  public String getProjectId() {
    return projectId;
  }

  public Boolean getAuthMethodHeader() {
    return authMethodHeader;
  }

  public Boolean getApiTokenPrivate() {
    return apiTokenPrivate;
  }

  public String getMergeRequestId() {
    return mergeRequestId;
  }

  public boolean isUseApiTokenCredentials() {
    return useApiTokenCredentials;
  }

  public String getApiTokenCredentialsId() {
    return apiTokenCredentialsId;
  }

  public boolean getIgnoreCertificateErrors() {
    return ignoreCertificateErrors;
  }

  public void setCreateCommentWithAllSingleFileComments(
      boolean createCommentWithAllSingleFileComments) {
    this.createCommentWithAllSingleFileComments = createCommentWithAllSingleFileComments;
  }

  public Boolean getUseApiToken() {
    return useApiToken;
  }

  public Boolean getUseApiTokenCredentials() {
    return useApiTokenCredentials;
  }

  public void setUseApiToken(Boolean useApiToken) {
    this.useApiToken = useApiToken;
  }

  public void setCommentOnlyChangedContent(boolean commentOnlyChangedContent) {
    this.commentOnlyChangedContent = commentOnlyChangedContent;
  }

  public void setViolationConfigs(List<ViolationConfig> violationConfigs) {
    this.violationConfigs = violationConfigs;
  }

  public void setGitLabUrl(String gitLabUrl) {
    this.gitLabUrl = gitLabUrl;
  }

  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }

  @Override
  public String toString() {
    return "ViolationsToGitLabConfig [commentOnlyChangedContent="
        + commentOnlyChangedContent
        + ", createCommentWithAllSingleFileComments="
        + createCommentWithAllSingleFileComments
        + ", violationConfigs="
        + violationConfigs
        + ", gitLabUrl="
        + gitLabUrl
        + ", apiToken="
        + apiToken
        + ", useApiToken="
        + useApiToken
        + ", projectId="
        + projectId
        + ", mergeRequestId="
        + mergeRequestId
        + ", useApiTokenCredentials="
        + useApiTokenCredentials
        + ", apiTokenCredentialsId="
        + apiTokenCredentialsId
        + ", ignoreCertificateErrors="
        + ignoreCertificateErrors
        + ", apiTokenPrivate="
        + apiTokenPrivate
        + ", authMethodHeader="
        + authMethodHeader
        + "]";
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
    result = prime * result + (commentOnlyChangedContent ? 1231 : 1237);
    result = prime * result + (createCommentWithAllSingleFileComments ? 1231 : 1237);
    result = prime * result + (gitLabUrl == null ? 0 : gitLabUrl.hashCode());
    result =
        prime * result + (ignoreCertificateErrors == null ? 0 : ignoreCertificateErrors.hashCode());
    result = prime * result + (mergeRequestId == null ? 0 : mergeRequestId.hashCode());
    result = prime * result + (projectId == null ? 0 : projectId.hashCode());
    result = prime * result + (useApiToken == null ? 0 : useApiToken.hashCode());
    result =
        prime * result + (useApiTokenCredentials == null ? 0 : useApiTokenCredentials.hashCode());
    result = prime * result + (violationConfigs == null ? 0 : violationConfigs.hashCode());
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
    ViolationsToGitLabConfig other = (ViolationsToGitLabConfig) obj;
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
    if (commentOnlyChangedContent != other.commentOnlyChangedContent) {
      return false;
    }
    if (createCommentWithAllSingleFileComments != other.createCommentWithAllSingleFileComments) {
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
    if (mergeRequestId == null) {
      if (other.mergeRequestId != null) {
        return false;
      }
    } else if (!mergeRequestId.equals(other.mergeRequestId)) {
      return false;
    }
    if (projectId == null) {
      if (other.projectId != null) {
        return false;
      }
    } else if (!projectId.equals(other.projectId)) {
      return false;
    }
    if (useApiToken == null) {
      if (other.useApiToken != null) {
        return false;
      }
    } else if (!useApiToken.equals(other.useApiToken)) {
      return false;
    }
    if (useApiTokenCredentials == null) {
      if (other.useApiTokenCredentials != null) {
        return false;
      }
    } else if (!useApiTokenCredentials.equals(other.useApiTokenCredentials)) {
      return false;
    }
    if (violationConfigs == null) {
      if (other.violationConfigs != null) {
        return false;
      }
    } else if (!violationConfigs.equals(other.violationConfigs)) {
      return false;
    }
    return true;
  }
}
