package org.jenkinsci.plugins.jvctgl.config;

import static com.google.common.base.Strings.isNullOrEmpty;
import static se.bjurr.violations.lib.util.Utils.firstNonNull;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.model.Item;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jenkinsci.plugins.jvctgl.ViolationsToGitLabGlobalConfiguration;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import se.bjurr.violations.lib.model.SEVERITY;

public class ViolationsToGitLabConfig extends AbstractDescribableImpl<ViolationsToGitLabConfig>
    implements Serializable {
  private static final long serialVersionUID = 4851568645021422528L;

  private boolean commentOnlyChangedContent;
  private boolean createCommentWithAllSingleFileComments;
  private List<ViolationConfig> violationConfigs;
  private String gitLabUrl;
  private String apiToken;
  private String projectId;
  private String mergeRequestIid;
  @Deprecated private transient String mergeRequestId;
  private String apiTokenCredentialsId;
  private Boolean ignoreCertificateErrors;
  private Boolean apiTokenPrivate;
  private Boolean authMethodHeader;
  private SEVERITY minSeverity;
  private Boolean keepOldComments;
  private String commentTemplate;
  private Boolean shouldSetWip;

  @DataBoundConstructor
  public ViolationsToGitLabConfig(
      final String gitLabUrl, final String projectId, final String mergeRequestIid) {
    this.gitLabUrl = gitLabUrl;
    this.projectId = projectId;
    this.mergeRequestIid = mergeRequestIid;
    this.keepOldComments = true;
    this.shouldSetWip = false;
  }

  public ViolationsToGitLabConfig(final ViolationsToGitLabConfig rhs) {
    this.violationConfigs = rhs.violationConfigs;
    this.createCommentWithAllSingleFileComments = rhs.createCommentWithAllSingleFileComments;
    this.commentOnlyChangedContent = rhs.commentOnlyChangedContent;
    this.gitLabUrl = rhs.gitLabUrl;
    this.apiToken = rhs.apiToken;
    this.projectId = rhs.projectId;
    this.mergeRequestIid = rhs.mergeRequestIid;
    this.apiTokenCredentialsId = rhs.apiTokenCredentialsId;
    this.ignoreCertificateErrors = rhs.ignoreCertificateErrors;
    this.apiTokenPrivate = rhs.apiTokenPrivate;
    this.authMethodHeader = rhs.authMethodHeader;
    this.minSeverity = rhs.minSeverity;
    this.keepOldComments = rhs.keepOldComments;
    this.commentTemplate = rhs.commentTemplate;
    this.shouldSetWip = rhs.shouldSetWip;
  }

  public String getCommentTemplate() {
    return commentTemplate;
  }

  @DataBoundSetter
  public void setCommentTemplate(final String commentTemplate) {
    this.commentTemplate = commentTemplate;
  }

  private Object readResolve() {
    if (isNullOrEmpty(mergeRequestIid)) {
      mergeRequestIid = mergeRequestId;
    }
    return this;
  }

  public ViolationsToGitLabConfig() {
    this.violationConfigs = new ArrayList<>();
  }

  public void applyDefaults(final ViolationsToGitLabGlobalConfiguration defaults) {
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
    if (this.minSeverity == null) {
      this.minSeverity = defaults.getMinSeverity();
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

  @DataBoundSetter
  public void setIgnoreCertificateErrors(final Boolean ignoreCertificateErrors) {
    this.ignoreCertificateErrors = ignoreCertificateErrors;
  }

  @DataBoundSetter
  public void setApiTokenPrivate(final Boolean apiTokenPrivate) {
    this.apiTokenPrivate = apiTokenPrivate;
  }

  public void setMergeRequestIid(final String mergeRequestIid) {
    this.mergeRequestIid = mergeRequestIid;
  }

  public void setProjectId(final String projectId) {
    this.projectId = projectId;
  }

  @DataBoundSetter
  public void setApiTokenCredentialsId(final String apiTokenCredentialsId) {
    this.apiTokenCredentialsId = apiTokenCredentialsId;
  }

  @DataBoundSetter
  public void setAuthMethodHeader(final Boolean authMethodHeader) {
    this.authMethodHeader = authMethodHeader;
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

  public String getMergeRequestIid() {
    return mergeRequestIid;
  }

  public String getApiTokenCredentialsId() {
    return apiTokenCredentialsId;
  }

  public Boolean getIgnoreCertificateErrors() {
    return firstNonNull(ignoreCertificateErrors, false);
  }

  @DataBoundSetter
  public void setCreateCommentWithAllSingleFileComments(
      final boolean createCommentWithAllSingleFileComments) {
    this.createCommentWithAllSingleFileComments = createCommentWithAllSingleFileComments;
  }

  public SEVERITY getMinSeverity() {
    return minSeverity;
  }

  @DataBoundSetter
  public void setMinSeverity(final SEVERITY minSeverity) {
    this.minSeverity = minSeverity;
  }

  @DataBoundSetter
  public void setCommentOnlyChangedContent(final boolean commentOnlyChangedContent) {
    this.commentOnlyChangedContent = commentOnlyChangedContent;
  }

  @DataBoundSetter
  public void setViolationConfigs(final List<ViolationConfig> violationConfigs) {
    this.violationConfigs = violationConfigs;
  }

  public void setGitLabUrl(final String gitLabUrl) {
    this.gitLabUrl = gitLabUrl;
  }

  @DataBoundSetter
  public void setApiToken(final String apiToken) {
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
        + ", projectId="
        + projectId
        + ", mergeRequestIid="
        + mergeRequestIid
        + ", apiTokenCredentialsId="
        + apiTokenCredentialsId
        + ", ignoreCertificateErrors="
        + ignoreCertificateErrors
        + ", apiTokenPrivate="
        + apiTokenPrivate
        + ", authMethodHeader="
        + authMethodHeader
        + ", minSeverity="
        + minSeverity
        + ", keepOldComments="
        + keepOldComments
        + ", shouldSetWip="
        + shouldSetWip
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
    result = prime * result + (keepOldComments == null ? 0 : keepOldComments.hashCode());
    result = prime * result + (mergeRequestIid == null ? 0 : mergeRequestIid.hashCode());
    result = prime * result + (minSeverity == null ? 0 : minSeverity.hashCode());
    result = prime * result + (projectId == null ? 0 : projectId.hashCode());
    result = prime * result + (shouldSetWip == null ? 0 : shouldSetWip.hashCode());
    result = prime * result + (violationConfigs == null ? 0 : violationConfigs.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ViolationsToGitLabConfig other = (ViolationsToGitLabConfig) obj;
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
    if (keepOldComments == null) {
      if (other.keepOldComments != null) {
        return false;
      }
    } else if (!keepOldComments.equals(other.keepOldComments)) {
      return false;
    }
    if (mergeRequestIid == null) {
      if (other.mergeRequestIid != null) {
        return false;
      }
    } else if (!mergeRequestIid.equals(other.mergeRequestIid)) {
      return false;
    }
    if (minSeverity != other.minSeverity) {
      return false;
    }
    if (projectId == null) {
      if (other.projectId != null) {
        return false;
      }
    } else if (!projectId.equals(other.projectId)) {
      return false;
    }
    if (shouldSetWip == null) {
      if (other.shouldSetWip != null) {
        return false;
      }
    } else if (!shouldSetWip.equals(other.shouldSetWip)) {
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

  public Boolean getKeepOldComments() {
    return keepOldComments;
  }

  public Boolean getShouldSetWip() {
    return shouldSetWip;
  }

  @DataBoundSetter
  public void setShouldSetWip(final Boolean shouldSetWip) {
    this.shouldSetWip = shouldSetWip;
  }

  @DataBoundSetter
  public void setKeepOldComments(final Boolean keepOldComments) {
    this.keepOldComments = keepOldComments;
  }

  @Extension
  public static class DescriptorImpl extends Descriptor<ViolationsToGitLabConfig> {
    @NonNull
    @Override
    public String getDisplayName() {
      return "Violations To GitHub Server Config";
    }

    @Restricted(NoExternalUse.class)
    public ListBoxModel doFillMinSeverityItems() {
      final ListBoxModel items = new ListBoxModel();
      items.add("Default, Global Config or Info", "");
      for (final SEVERITY severity : SEVERITY.values()) {
        items.add(severity.name());
      }
      return items;
    }

    @SuppressWarnings("unused") // Used by stapler
    public ListBoxModel doFillApiTokenCredentialsIdItems(
        @AncestorInPath final Item item,
        @QueryParameter final String apiTokenCredentialsId,
        @QueryParameter final String gitLabUrl) {
      return CredentialsHelper.doFillApiTokenCredentialsIdItems(
          item, apiTokenCredentialsId, gitLabUrl);
    }

    @SuppressWarnings("unused") // Used by stapler
    public FormValidation doCheckApiTokenCredentialsId(
        @AncestorInPath final Item item,
        @QueryParameter final String value,
        @QueryParameter final String gitLabUrl) {
      return CredentialsHelper.doCheckApiTokenCredentialsId(item, value, gitLabUrl);
    }
  }
}
