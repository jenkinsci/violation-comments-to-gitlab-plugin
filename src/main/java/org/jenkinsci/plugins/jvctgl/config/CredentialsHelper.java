package org.jenkinsci.plugins.jvctgl.config;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Strings.isNullOrEmpty;

import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
import com.cloudbees.plugins.credentials.domains.URIRequirementBuilder;
import com.google.common.base.Optional;
import hudson.model.Item;
import hudson.model.Queue;
import hudson.model.queue.Tasks;
import hudson.security.ACL;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;

/* Read more about credentials api here: https://github.com/jenkinsci/credentials-plugin/blob/master/docs/consumer.adoc */
public class CredentialsHelper {
  public static ListBoxModel doFillApiTokenCredentialsIdItems(
      Item item, String credentialsId, String uri) {
    StandardListBoxModel result = new StandardListBoxModel();
    if (item == null) {
      if (!Jenkins.getInstance().hasPermission(Jenkins.ADMINISTER)) {
        return result.includeCurrentValue(credentialsId);
      }
    } else {
      if (!item.hasPermission(Item.EXTENDED_READ)
          && !item.hasPermission(CredentialsProvider.USE_ITEM)) {
        return result.includeCurrentValue(credentialsId);
      }
    }
    return result //
        .includeEmptyValue() //
        .includeMatchingAs(
            item instanceof Queue.Task ? Tasks.getAuthenticationOf((Queue.Task) item) : ACL.SYSTEM,
            item,
            StandardCredentials.class,
            URIRequirementBuilder.fromUri(uri).build(),
            CredentialsMatchers.anyOf(CredentialsMatchers.instanceOf(StringCredentials.class)))
        .includeCurrentValue(credentialsId);
  }

  public static FormValidation doCheckApiTokenCredentialsId(
      Item item, String credentialsId, String uri) {
    if (item == null) {
      if (!Jenkins.getInstance().hasPermission(Jenkins.ADMINISTER)) {
        return FormValidation.ok();
      }
    } else {
      if (!item.hasPermission(Item.EXTENDED_READ)
          && !item.hasPermission(CredentialsProvider.USE_ITEM)) {
        return FormValidation.ok();
      }
    }
    if (isNullOrEmpty(credentialsId)) {
      return FormValidation.ok();
    }
    if (!findApiTokenCredentials(item, credentialsId, uri).isPresent()) {
      return FormValidation.error("Cannot find currently selected credentials");
    }
    return FormValidation.ok();
  }

  public static Optional<StringCredentials> findApiTokenCredentials(
      Item item, String credentialsId, String uri) {
    if (isNullOrEmpty(credentialsId)) {
      return absent();
    }
    return fromNullable(
        CredentialsMatchers.firstOrNull(
            CredentialsProvider.lookupCredentials(
                StringCredentials.class,
                item,
                item instanceof Queue.Task
                    ? Tasks.getAuthenticationOf((Queue.Task) item)
                    : ACL.SYSTEM,
                URIRequirementBuilder.fromUri(uri).build()),
            CredentialsMatchers.allOf(
                CredentialsMatchers.withId(credentialsId),
                CredentialsMatchers.instanceOf(StringCredentials.class))));
  }
}
