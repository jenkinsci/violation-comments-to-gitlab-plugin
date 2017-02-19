package org.jenkinsci.plugins.jvctgl;

import static hudson.tasks.BuildStepMonitor.NONE;
import static org.jenkinsci.plugins.jvctgl.perform.JvctglPerformer.jvctsPerform;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.TaskListener;
import hudson.model.Run;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

import java.io.IOException;

import javax.annotation.Nonnull;

import jenkins.tasks.SimpleBuildStep;

import org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfig;
import org.kohsuke.stapler.DataBoundConstructor;

public class ViolationsToGitLabRecorder extends Recorder implements SimpleBuildStep {
  @Extension
  public static final BuildStepDescriptor<Publisher> DESCRIPTOR =
      new ViolationsToGitLabDescriptor();

  private ViolationsToGitLabConfig config;

  public ViolationsToGitLabRecorder() {}

  @DataBoundConstructor
  public ViolationsToGitLabRecorder(ViolationsToGitLabConfig config) {
    this.config = config;
  }

  public ViolationsToGitLabConfig getConfig() {
    return this.config;
  }

  @Override
  public BuildStepDescriptor<Publisher> getDescriptor() {
    return DESCRIPTOR;
  }

  @Override
  public BuildStepMonitor getRequiredMonitorService() {
    return NONE;
  }

  @Override
  public void perform(
      @Nonnull Run<?, ?> build,
      @Nonnull FilePath filePath,
      @Nonnull Launcher launcher,
      @Nonnull TaskListener listener)
      throws InterruptedException, IOException {

    ViolationsToGitLabConfig combinedConfig = new ViolationsToGitLabConfig(this.config);
    ViolationsToGitLabGlobalConfiguration defaults = ViolationsToGitLabGlobalConfiguration.get();

    combinedConfig.applyDefaults(defaults);

    jvctsPerform(combinedConfig, filePath, build, listener);
  }

  public void setConfig(ViolationsToGitLabConfig config) {
    this.config = config;
  }
}
