package org.jenkinsci.plugins.jvctgl;

import static hudson.tasks.BuildStepMonitor.NONE;
import static org.jenkinsci.plugins.jvctgl.perform.JvctglPerformer.jvctsPerform;

import java.io.IOException;

import org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfig;
import org.kohsuke.stapler.DataBoundConstructor;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import jenkins.tasks.SimpleBuildStep;

public class ViolationsToGitLabRecorder extends Recorder implements SimpleBuildStep {

  public static final BuildStepDescriptor<Publisher> DESCRIPTOR =
      new ViolationsToGitLabDescriptor();

  private ViolationsToGitLabConfig config;

  public ViolationsToGitLabRecorder() {}

  @DataBoundConstructor
  public ViolationsToGitLabRecorder(final ViolationsToGitLabConfig config) {
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
      @NonNull final Run<?, ?> build,
      @NonNull final FilePath filePath,
      @NonNull final Launcher launcher,
      @NonNull final TaskListener listener)
      throws InterruptedException, IOException {

    final ViolationsToGitLabConfig combinedConfig = new ViolationsToGitLabConfig(this.config);
    final ViolationsToGitLabGlobalConfiguration defaults =
        ViolationsToGitLabGlobalConfiguration.get();

    combinedConfig.applyDefaults(defaults);

    jvctsPerform(combinedConfig, filePath, build, listener);
  }

  public void setConfig(final ViolationsToGitLabConfig config) {
    this.config = config;
  }
}
