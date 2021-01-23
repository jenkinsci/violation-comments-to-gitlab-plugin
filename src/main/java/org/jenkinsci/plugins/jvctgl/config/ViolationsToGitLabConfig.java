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
import java.util.Objects;
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
  private boolean commentOnlyChangedFiles = true;
  private boolean createCommentWithAllSingleFileComments;
  private boolean createSingleFileComments;
  private List<ViolationConfig> violationConfigs;
  private String gitLabUrl;
  private String projectId;
  private String mergeRequestIid;
  private String apiTokenCredentialsId;
  private Boolean ignoreCertificateErrors;
  private Boolean apiTokenPrivate;
  private Boolean authMethodHeader;
  private SEVERITY minSeverity;
  private Boolean keepOldComments;
  private String commentTemplate;
  private Boolean shouldSetWip;
  private String proxyUri;
  private String proxyCredentialsId;
  private Boolean enableLogging;
  private Integer maxNumberOfViolations;

  @DataBoundConstructor
  public ViolationsToGitLabConfig(
      final String gitLabUrl,
      final String projectId,
      final String mergeRequestIid,
      final String apiTokenCredentialsId) {
    this.gitLabUrl = gitLabUrl;
    this.projectId = projectId;
    this.mergeRequestIid = mergeRequestIid;
    this.keepOldComments = true;
    this.shouldSetWip = false;
    this.enableLogging = false;
    this.maxNumberOfViolations = null;
    this.apiTokenCredentialsId = apiTokenCredentialsId;
  }

  public ViolationsToGitLabConfig(final ViolationsToGitLabConfig rhs) {
    this.violationConfigs = rhs.violationConfigs;
    this.createCommentWithAllSingleFileComments = rhs.createCommentWithAllSingleFileComments;
    this.createSingleFileComments = rhs.createSingleFileComments;
    this.commentOnlyChangedContent = rhs.commentOnlyChangedContent;
    this.commentOnlyChangedFiles = rhs.commentOnlyChangedFiles;
    this.gitLabUrl = rhs.gitLabUrl;
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
    this.proxyUri = rhs.proxyUri;
    this.proxyCredentialsId = rhs.proxyCredentialsId;
    this.enableLogging = rhs.enableLogging;
    this.maxNumberOfViolations = rhs.maxNumberOfViolations;
  }

  public String getCommentTemplate() {
    return this.commentTemplate;
  }

  @DataBoundSetter
  public void setCommentTemplate(final String commentTemplate) {
    this.commentTemplate = commentTemplate;
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
    if (isNullOrEmpty(this.apiTokenCredentialsId)) {
      this.apiTokenCredentialsId = defaults.getApiTokenCredentialsId();
    }
    if (this.ignoreCertificateErrors == null) {
      this.ignoreCertificateErrors = defaults.isIgnoreCertificateErrors();
    }
    if (this.apiTokenPrivate == null) {
      this.apiTokenPrivate = defaults.isApiTokenPrivate();
    }
    if (this.authMethodHeader == null) {
      this.authMethodHeader = defaults.isAuthMethodHeader();
    }
    if (this.minSeverity == null) {
      this.minSeverity = defaults.getMinSeverity();
    }
  }

  public String getGitLabUrl() {
    return this.gitLabUrl;
  }

  public boolean getCommentOnlyChangedContent() {
    return this.commentOnlyChangedContent;
  }

  public boolean getCreateCommentWithAllSingleFileComments() {
    return this.createCommentWithAllSingleFileComments;
  }

  public boolean getCreateSingleFileComments() {
    return this.createSingleFileComments;
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
  public void setAuthMethodHeader(final Boolean authMethodHeader) {
    this.authMethodHeader = authMethodHeader;
  }

  public String getProjectId() {
    return this.projectId;
  }

  public Boolean getAuthMethodHeader() {
    return this.authMethodHeader;
  }

  public Boolean getApiTokenPrivate() {
    return this.apiTokenPrivate;
  }

  public String getMergeRequestIid() {
    return this.mergeRequestIid;
  }

  public String getApiTokenCredentialsId() {
    return this.apiTokenCredentialsId;
  }

  public Boolean getIgnoreCertificateErrors() {
    return firstNonNull(this.ignoreCertificateErrors, false);
  }

  @DataBoundSetter
  public void setCreateCommentWithAllSingleFileComments(
      final boolean createCommentWithAllSingleFileComments) {
    this.createCommentWithAllSingleFileComments = createCommentWithAllSingleFileComments;
  }

  @DataBoundSetter
  public void setCreateSingleFileComments(final boolean createSingleFileComments) {
    this.createSingleFileComments = createSingleFileComments;
  }

  public SEVERITY getMinSeverity() {
    return this.minSeverity;
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
  public void setCommentOnlyChangedFiles(final boolean commentOnlyChangedFiles) {
    this.commentOnlyChangedFiles = commentOnlyChangedFiles;
  }

  public boolean getCommentOnlyChangedFiles() {
    return this.commentOnlyChangedFiles;
  }

  @DataBoundSetter
  public void setViolationConfigs(final List<ViolationConfig> violationConfigs) {
    this.violationConfigs = violationConfigs;
  }

  public void setGitLabUrl(final String gitLabUrl) {
    this.gitLabUrl = gitLabUrl;
  }

  public void setApiTokenCredentialsId(final String apiTokenCredentialsId) {
    this.apiTokenCredentialsId = apiTokenCredentialsId;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final ViolationsToGitLabConfig that = (ViolationsToGitLabConfig) o;
    return this.commentOnlyChangedContent == that.commentOnlyChangedContent
        && this.commentOnlyChangedFiles == that.commentOnlyChangedFiles
        && this.createCommentWithAllSingleFileComments
            == that.createCommentWithAllSingleFileComments
        && this.createSingleFileComments == that.createSingleFileComments
        && Objects.equals(this.violationConfigs, that.violationConfigs)
        && Objects.equals(this.gitLabUrl, that.gitLabUrl)
        && Objects.equals(this.projectId, that.projectId)
        && Objects.equals(this.mergeRequestIid, that.mergeRequestIid)
        && Objects.equals(this.apiTokenCredentialsId, that.apiTokenCredentialsId)
        && Objects.equals(this.ignoreCertificateErrors, that.ignoreCertificateErrors)
        && Objects.equals(this.apiTokenPrivate, that.apiTokenPrivate)
        && Objects.equals(this.authMethodHeader, that.authMethodHeader)
        && this.minSeverity == that.minSeverity
        && Objects.equals(this.keepOldComments, that.keepOldComments)
        && Objects.equals(this.commentTemplate, that.commentTemplate)
        && Objects.equals(this.shouldSetWip, that.shouldSetWip)
        && Objects.equals(this.proxyUri, that.proxyUri)
        && Objects.equals(this.proxyCredentialsId, that.proxyCredentialsId)
        && Objects.equals(this.enableLogging, that.enableLogging);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.commentOnlyChangedContent,
        this.commentOnlyChangedFiles,
        this.createCommentWithAllSingleFileComments,
        this.createSingleFileComments,
        this.violationConfigs,
        this.gitLabUrl,
        this.projectId,
        this.mergeRequestIid,
        this.apiTokenCredentialsId,
        this.ignoreCertificateErrors,
        this.apiTokenPrivate,
        this.authMethodHeader,
        this.minSeverity,
        this.keepOldComments,
        this.commentTemplate,
        this.shouldSetWip,
        this.proxyUri,
        this.proxyCredentialsId,
        this.enableLogging);
  }

  @Override
  public String toString() {
    return "ViolationsToGitLabConfig{"
        + "commentOnlyChangedContent="
        + this.commentOnlyChangedContent
        + "commentOnlyChangedFiles="
        + this.commentOnlyChangedFiles
        + ", createCommentWithAllSingleFileComments="
        + this.createCommentWithAllSingleFileComments
        + ", createSingleFileComments="
        + this.createSingleFileComments
        + ", violationConfigs="
        + this.violationConfigs
        + ", gitLabUrl='"
        + this.gitLabUrl
        + '\''
        + ", projectId='"
        + this.projectId
        + '\''
        + ", mergeRequestIid='"
        + this.mergeRequestIid
        + '\''
        + ", apiTokenCredentialsId='"
        + this.apiTokenCredentialsId
        + '\''
        + ", ignoreCertificateErrors="
        + this.ignoreCertificateErrors
        + ", apiTokenPrivate="
        + this.apiTokenPrivate
        + ", authMethodHeader="
        + this.authMethodHeader
        + ", minSeverity="
        + this.minSeverity
        + ", keepOldComments="
        + this.keepOldComments
        + ", commentTemplate='"
        + this.commentTemplate
        + '\''
        + ", shouldSetWip="
        + this.shouldSetWip
        + '}';
  }

  public Boolean getKeepOldComments() {
    return this.keepOldComments;
  }

  public Boolean getShouldSetWip() {
    return this.shouldSetWip;
  }

  @DataBoundSetter
  public void setShouldSetWip(final Boolean shouldSetWip) {
    this.shouldSetWip = shouldSetWip;
  }

  @DataBoundSetter
  public void setKeepOldComments(final Boolean keepOldComments) {
    this.keepOldComments = keepOldComments;
  }

  @DataBoundSetter
  public void setProxyUri(final String proxyUri) {
    this.proxyUri = proxyUri;
  }

  public String getProxyUri() {
    return this.proxyUri;
  }

  @DataBoundSetter
  public void setProxyCredentialsId(final String proxyCredentialsId) {
    this.proxyCredentialsId = proxyCredentialsId;
  }

  public String getProxyCredentialsId() {
    return this.proxyCredentialsId;
  }

  @DataBoundSetter
  public void setEnableLogging(final Boolean enableLogging) {
    this.enableLogging = enableLogging;
  }

  public Boolean getEnableLogging() {
    return this.enableLogging;
  }

  public Integer getMaxNumberOfViolations() {
    return this.maxNumberOfViolations;
  }

  @DataBoundSetter
  public void setMaxNumberOfViolations(final Integer maxNumberOfViolations) {
    this.maxNumberOfViolations = maxNumberOfViolations;
  }

  @Extension
  public static class DescriptorImpl extends Descriptor<ViolationsToGitLabConfig> {
    @NonNull
    @Override
    public String getDisplayName() {
      return "Violations To GitLab Server Config";
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
    public ListBoxModel doFillProxyCredentialsIdItems(
        @AncestorInPath final Item item,
        @QueryParameter final String apiTokenCredentialsId,
        @QueryParameter final String gitLabUrl) {
      return CredentialsHelper.doFillUserNamePasswordCredentialsIdItems();
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
