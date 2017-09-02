package org.jenkinsci.plugins.jvctgl.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import se.bjurr.violations.lib.reports.Parser;

public class ViolationsToGitLabConfigHelper {
  public static final String FIELD_COMMENTONLYCHANGEDCONTENT = "commentOnlyChangedContent";
  public static final String FIELD_CREATECOMMENTWITHALLSINGLEFILECOMMENTS =
      "createCommentWithAllSingleFileComments";
  public static final String FIELD_CREATESINGLEFILECOMMENTS = "createSingleFileComments";
  public static final String FIELD_GITLABURL = "gitLabUrl";
  public static final String FIELD_PATTERN = "pattern";
  public static final String FIELD_REPORTER = "reporter";
  public static final String FIELD_PARSER = "parser";
  public static final String FIELD_PROJECTID = "projectId";
  public static final String FIELD_MERGEREQUESTID = "mergeRequestId";
  public static final String FIELD_APITOKEN = "apiToken";
  public static final String FIELD_USEAPITOKENCREDENTIALS = "useApiTokenCredentials";
  public static final String FIELD_USEAPITOKEN = "useApiToken";
  public static final String FIELD_APITOKENCREDENTIALSID = "apiTokenCredentialsId";
  public static final String FIELD_IGNORECERTIFICATEERRORS = "ignoreCertificateErrors";
  public static final String FIELD_APITOKENPRIVATE = "apiTokenPrivate";
  public static final String FIELD_AUTHMETHODHEADER = "authMethodHeader";
  public static final String FIELD_MINSEVERITY = "minSeverity";
  public static final String FIELD_KEEP_OLD_COMMENTS = "keepOldComments";
  public static final String FIELD_SHOULD_SET_WIP = "shouldSetWip";

  public static ViolationsToGitLabConfig createNewConfig() {
    final ViolationsToGitLabConfig config = new ViolationsToGitLabConfig();
    final List<ViolationConfig> violationConfigs = getAllViolationConfigs();
    config.setViolationConfigs(violationConfigs);
    return config;
  }

  public static List<ViolationConfig> getAllViolationConfigs() {
    final List<ViolationConfig> violationConfigs = newArrayList();
    for (final Parser parser : Parser.values()) {
      final ViolationConfig violationConfig = new ViolationConfig();
      violationConfig.setParser(parser);
      violationConfigs.add(violationConfig);
    }
    return violationConfigs;
  }
}
