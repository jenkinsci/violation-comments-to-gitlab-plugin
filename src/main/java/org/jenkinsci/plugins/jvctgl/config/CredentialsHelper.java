package org.jenkinsci.plugins.jvctgl.config;

import static com.cloudbees.plugins.credentials.CredentialsMatchers.allOf;
import static com.cloudbees.plugins.credentials.CredentialsMatchers.firstOrNull;
import static com.cloudbees.plugins.credentials.CredentialsMatchers.withId;
import static com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials;
import static com.google.common.base.Strings.isNullOrEmpty;
import static hudson.security.ACL.SYSTEM;

import com.cloudbees.plugins.credentials.Credentials;
import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
import com.cloudbees.plugins.credentials.common.StandardUsernameListBoxModel;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;
import com.cloudbees.plugins.credentials.domains.DomainRequirement;
import com.cloudbees.plugins.credentials.domains.URIRequirementBuilder;
import hudson.model.Item;
import hudson.model.ItemGroup;
import hudson.model.Queue;
import hudson.model.queue.Tasks;
import hudson.security.ACL;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import java.util.List;
import java.util.Optional;
import jenkins.model.Jenkins;
import org.acegisecurity.Authentication;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;

/* Read more about credentials api here: https://github.com/jenkinsci/credentials-plugin/blob/master/docs/consumer.adoc */
public class CredentialsHelper {
  public static ListBoxModel doFillApiTokenCredentialsIdItems(
      final Item item, final String credentialsId, final String uri) {
    final StandardListBoxModel result = new StandardListBoxModel();
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
      final Item item, final String credentialsId, final String uri) {
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
      final Item item, final String credentialsId, final String uri) {
    if (isNullOrEmpty(credentialsId)) {
      return Optional.empty();
    }
    return Optional.ofNullable(
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

  public static Optional<StandardUsernamePasswordCredentials> findUserCredentials(
      final String apiTokenCredentialsId) {
    if (isNullOrEmpty(apiTokenCredentialsId)) {
      return Optional.empty();
    }

    return Optional.ofNullable(
        firstOrNull(
            getAllCredentials(StandardUsernamePasswordCredentials.class),
            allOf(withId(apiTokenCredentialsId))));
  }

  public static ListBoxModel doFillUserNamePasswordCredentialsIdItems() {
    final List<StandardUsernamePasswordCredentials> credentials =
        getAllCredentials(StandardUsernamePasswordCredentials.class);
    return new StandardUsernameListBoxModel() //
        .includeEmptyValue() //
        .withAll(credentials);
  }

  private static <C extends Credentials> List<C> getAllCredentials(final Class<C> type) {
    final ItemGroup<?> itemGroup = null;
    final Authentication authentication = SYSTEM;
    final DomainRequirement domainRequirement = null;

    return lookupCredentials(type, itemGroup, authentication, domainRequirement);
  }
}
