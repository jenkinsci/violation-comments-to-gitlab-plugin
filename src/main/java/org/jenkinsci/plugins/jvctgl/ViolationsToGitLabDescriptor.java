package org.jenkinsci.plugins.jvctgl;

import static org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfigHelper.FIELD_MINSEVERITY;

import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfig;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import net.sf.json.JSONObject;

@Extension
@Symbol("ViolationsToGitLab")
public final class ViolationsToGitLabDescriptor extends BuildStepDescriptor<Publisher> {
  private ViolationsToGitLabConfig config;

  public ViolationsToGitLabDescriptor() {
    super(ViolationsToGitLabRecorder.class);
    load();
    if (this.config == null) {
      this.config = new ViolationsToGitLabConfig();
    }
  }

  @Override
  public String getDisplayName() {
    return "Report Violations to GitLab";
  }

  @Override
  public String getHelpFile() {
    return super.getHelpFile();
  }

  @Override
  public boolean isApplicable(
      @SuppressWarnings("rawtypes") final Class<? extends AbstractProject> jobType) {
    return true;
  }

  @Override
  public Publisher newInstance(final StaplerRequest req, final JSONObject formData)
      throws hudson.model.Descriptor.FormException {
    if (formData != null) {
      final JSONObject config = formData.getJSONObject("config");
      final String minSeverity = config.getString(FIELD_MINSEVERITY);
      if (StringUtils.isBlank(minSeverity)) {
        config.remove(FIELD_MINSEVERITY);
      }
    }

    return req.bindJSON(ViolationsToGitLabRecorder.class, formData);
  }
}
