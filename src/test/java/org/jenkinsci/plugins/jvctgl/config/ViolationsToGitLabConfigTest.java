package org.jenkinsci.plugins.jvctgl.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.co.jemos.podam.api.PodamFactoryImpl;

public class ViolationsToGitLabConfigTest {

  @Test
  public void testThatCopyConstructorCopiesEverything() {
    ViolationsToGitLabConfig original =
        new PodamFactoryImpl().manufacturePojo(ViolationsToGitLabConfig.class);
    ViolationsToGitLabConfig actual = new ViolationsToGitLabConfig(original);
    String message = "All config should be copied.";
    assertEquals(message, original, actual);
  }
}
