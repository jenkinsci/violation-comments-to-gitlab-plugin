package org.jenkinsci.plugins.jvctgl.perform;

import static org.junit.Assert.assertEquals;

import hudson.EnvVars;
import org.jenkinsci.plugins.jvctgl.config.ViolationsToGitLabConfig;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class JvctglPerformerTest {

  @Test
  public void testThatAllVariablesAreExpanded() {
    ViolationsToGitLabConfig original =
        new PodamFactoryImpl().manufacturePojo(ViolationsToGitLabConfig.class);

    ViolationsToGitLabConfig expanded =
        JvctglPerformer.expand(
            original,
            new EnvVars() {
              private static final long serialVersionUID = 3950742092801432326L;

              @Override
              public String expand(String s) {
                return s;
              }
            });

    String message = "All variables should be expanded";
    assertEquals(message, original.toString(), expanded.toString());
  }
}
