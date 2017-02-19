package org.jenkinsci.plugins.jvctgl.config;

import static com.cloudbees.plugins.credentials.CredentialsMatchers.allOf;
import static com.cloudbees.plugins.credentials.CredentialsMatchers.firstOrNull;
import static com.cloudbees.plugins.credentials.CredentialsMatchers.withId;
import static com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials;
import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Strings.isNullOrEmpty;
import static hudson.security.ACL.SYSTEM;

import java.util.List;

import org.acegisecurity.Authentication;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;

import com.cloudbees.plugins.credentials.Credentials;
import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
import com.cloudbees.plugins.credentials.domains.DomainRequirement;
import com.google.common.base.Optional;

import hudson.model.ItemGroup;
import hudson.util.ListBoxModel;

public class CredentialsHelper {

  public static ListBoxModel doFillApiTokenCredentialsIdItems() {
    List<StringCredentials> credentials = getAllCredentials(StringCredentials.class);
    ListBoxModel listBoxModel =
        new StandardListBoxModel() //
            .includeEmptyValue() //
            .withAll(credentials);
    return listBoxModel;
  }

  public static Optional<StringCredentials> findApiTokenCredentials(String apiTokenCredentialsId) {
    if (isNullOrEmpty(apiTokenCredentialsId)) {
      return absent();
    }

    return fromNullable(
        firstOrNull(
            getAllCredentials(StringCredentials.class), allOf(withId(apiTokenCredentialsId))));
  }

  private static <C extends Credentials> List<C> getAllCredentials(Class<C> type) {
    ItemGroup<?> itemGroup = null;
    Authentication authentication = SYSTEM;
    DomainRequirement domainRequirement = null;

    return lookupCredentials(type, itemGroup, authentication, domainRequirement);
  }
}
