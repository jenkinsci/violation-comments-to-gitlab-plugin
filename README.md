# Violation Comments to GitLab Plugin

[![Build Status](https://ci.jenkins.io/job/Plugins/job/violation-comments-to-gitlab-plugin/job/master/badge/icon)](https://ci.jenkins.io/job/Plugins/job/violation-comments-to-gitlab-plugin)

This is a Jenkins plugin for [Violation Comments to GitLab Lib](https://github.com/tomasbjerre/violation-comments-to-gitlab-lib). This plugin will find report files from static code analysis and comment GitLab pull requests with the content.

Available in Jenkins [here](https://wiki.jenkins-ci.org/display/JENKINS/Violation+Comments+to+GitLab+Plugin).

Example of supported reports are available [here](https://github.com/tomasbjerre/violations-lib/tree/master/src/test/resources).

There is a complete running example available here: https://github.com/tomasbjerre/jenkins-configuration-as-code-sandbox

You can also do this with a [command line tool](https://www.npmjs.com/package/violation-comments-to-gitlab-command-line).

A number of **parsers** have been implemented. Some **parsers** can parse output from several **reporters**.

| Reporter | Parser | Notes
| --- | --- | ---
| [_ARM-GCC_](https://developer.arm.com/open-source/gnu-toolchain/gnu-rm)               | `CLANG`              | 
| [_AndroidLint_](http://developer.android.com/tools/help/lint.html)                    | `ANDROIDLINT`        | 
| [_AnsibleLint_](https://github.com/willthames/ansible-lint)                           | `FLAKE8`             | With `-p`
| [_Bandit_](https://github.com/PyCQA/bandit)                                           | `CLANG`              | With `bandit -r examples/ -f custom -o bandit.out --msg-template "{abspath}:{line}: {severity}: {test_id}: {msg}"`
| [_CLang_](https://clang-analyzer.llvm.org/)                                           | `CLANG`              | 
| [_CPD_](http://pmd.sourceforge.net/pmd-4.3.0/cpd.html)                                | `CPD`                | 
| [_CPPCheck_](http://cppcheck.sourceforge.net/)                                        | `CPPCHECK`           | With `cppcheck test.cpp --output-file=cppcheck.xml --xml`
| [_CPPLint_](https://github.com/theandrewdavis/cpplint)                                | `CPPLINT`            | 
| [_CSSLint_](https://github.com/CSSLint/csslint)                                       | `CSSLINT`            | 
| [_Checkstyle_](http://checkstyle.sourceforge.net/)                                    | `CHECKSTYLE`         | 
| [_CloudFormation Linter_](https://github.com/aws-cloudformation/cfn-lint)             | `JUNIT`              | `cfn-lint . -f junit --output-file report-junit.xml`
| [_CodeClimate_](https://codeclimate.com/)                                             | `CODECLIMATE`        | 
| [_CodeNarc_](http://codenarc.sourceforge.net/)                                        | `CODENARC`           | 
| [_Detekt_](https://github.com/arturbosch/detekt)                                      | `CHECKSTYLE`         | With `--output-format xml`.
| [_DocFX_](http://dotnet.github.io/docfx/)                                             | `DOCFX`              | 
| [_Doxygen_](https://www.stack.nl/~dimitri/doxygen/)                                   | `CLANG`              | 
| [_ERB_](https://www.puppetcookbook.com/posts/erb-template-validation.html)            | `CLANG`              | With `erb -P -x -T '-' "${it}" \| ruby -c 2>&1 >/dev/null \| grep '^-' \| sed -E 's/^-([a-zA-Z0-9:]+)/${filename}\1 ERROR:/p' > erbfiles.out`.
| [_ESLint_](https://github.com/sindresorhus/grunt-eslint)                              | `CHECKSTYLE`         | With `format: 'checkstyle'`.
| [_Findbugs_](http://findbugs.sourceforge.net/)                                        | `FINDBUGS`           | 
| [_Flake8_](http://flake8.readthedocs.org/en/latest/)                                  | `FLAKE8`             | 
| [_FxCop_](https://en.wikipedia.org/wiki/FxCop)                                        | `FXCOP`              | 
| [_GCC_](https://gcc.gnu.org/)                                                         | `CLANG`              | 
| [_Gendarme_](http://www.mono-project.com/docs/tools+libraries/tools/gendarme/)        | `GENDARME`           | 
| [_Generic reporter_]()                                                                | `GENERIC`            | Will create one single violation with all the content as message.
| [_GoLint_](https://github.com/golang/lint)                                            | `GOLINT`             | 
| [_GoVet_](https://golang.org/cmd/vet/)                                                | `GOLINT`             | Same format as GoLint.
| [_GolangCI-Lint_](https://github.com/golangci/golangci-lint/)                         | `CHECKSTYLE`         | With `--out-format=checkstyle`.
| [_GoogleErrorProne_](https://github.com/google/error-prone)                           | `GOOGLEERRORPRONE`   | 
| [_HadoLint_](https://github.com/hadolint/hadolint/)                                   | `CHECKSTYLE`         | With `-f checkstyle`
| [_IAR_](https://www.iar.com/iar-embedded-workbench/)                                  | `IAR`                | With `--no_wrap_diagnostics`
| [_Infer_](http://fbinfer.com/)                                                        | `PMD`                | Facebook Infer. With `--pmd-xml`.
| [_JACOCO_](https://www.jacoco.org/)                                                   | `JACOCO`             | 
| [_JCReport_](https://github.com/jCoderZ/fawkez/wiki/JcReport)                         | `JCREPORT`           | 
| [_JSHint_](http://jshint.com/)                                                        | `JSLINT`             | With `--reporter=jslint` or the CHECKSTYLE parser with `--reporter=checkstyle`
| [_JUnit_](https://junit.org/junit4/)                                                  | `JUNIT`              | It only contains the failures.
| [_KTLint_](https://github.com/shyiko/ktlint)                                          | `CHECKSTYLE`         | 
| [_Klocwork_](http://www.klocwork.com/products-services/klocwork/static-code-analysis)  | `KLOCWORK`           | 
| [_KotlinGradle_](https://github.com/JetBrains/kotlin)                                 | `KOTLINGRADLE`       | Output from Kotlin Gradle Plugin.
| [_KotlinMaven_](https://github.com/JetBrains/kotlin)                                  | `KOTLINMAVEN`        | Output from Kotlin Maven Plugin.
| [_Lint_]()                                                                            | `LINT`               | A common XML format, used by different linters.
| [_MSBuildLog_](https://docs.microsoft.com/en-us/visualstudio/msbuild/obtaining-build-logs-with-msbuild?view=vs-2019)  | `MSBULDLOG`          | With `-fileLogger` use `.*msbuild\\.log$` as pattern or `-fl -flp:logfile=MyProjectOutput.log;verbosity=diagnostic` for a custom output filename
| [_MSCpp_](https://visualstudio.microsoft.com/vs/features/cplusplus/)                  | `MSCPP`              | 
| [_Mccabe_](https://pypi.python.org/pypi/mccabe)                                       | `FLAKE8`             | 
| [_MyPy_](https://pypi.python.org/pypi/mypy-lang)                                      | `MYPY`               | 
| [_NullAway_](https://github.com/uber/NullAway)                                        | `GOOGLEERRORPRONE`   | Same format as Google Error Prone.
| [_PCLint_](http://www.gimpel.com/html/pcl.htm)                                        | `PCLINT`             | PC-Lint using the same output format as the Jenkins warnings plugin, [_details here_](https://wiki.jenkins.io/display/JENKINS/PcLint+options)
| [_PHPCS_](https://github.com/squizlabs/PHP_CodeSniffer)                               | `CHECKSTYLE`         | With `phpcs api.php --report=checkstyle`.
| [_PHPPMD_](https://phpmd.org/)                                                        | `PMD`                | With `phpmd api.php xml ruleset.xml`.
| [_PMD_](https://pmd.github.io/)                                                       | `PMD`                | 
| [_Pep8_](https://github.com/PyCQA/pycodestyle)                                        | `FLAKE8`             | 
| [_PerlCritic_](https://github.com/Perl-Critic)                                        | `PERLCRITIC`         | 
| [_PiTest_](http://pitest.org/)                                                        | `PITEST`             | 
| [_ProtoLint_](https://github.com/yoheimuta/protolint)                                 | `PROTOLINT`          | 
| [_Puppet-Lint_](http://puppet-lint.com/)                                              | `CLANG`              | With `-log-format %{fullpath}:%{line}:%{column}: %{kind}: %{message}`
| [_PyDocStyle_](https://pypi.python.org/pypi/pydocstyle)                               | `PYDOCSTYLE`         | 
| [_PyFlakes_](https://pypi.python.org/pypi/pyflakes)                                   | `FLAKE8`             | 
| [_PyLint_](https://www.pylint.org/)                                                   | `PYLINT`             | With `pylint --output-format=parseable`.
| [_ReSharper_](https://www.jetbrains.com/resharper/)                                   | `RESHARPER`          | 
| [_RubyCop_](http://rubocop.readthedocs.io/en/latest/formatters/)                      | `CLANG`              | With `rubycop -f clang file.rb`
| [_SbtScalac_](http://www.scala-sbt.org/)                                              | `SBTSCALAC`          | 
| [_Scalastyle_](http://www.scalastyle.org/)                                            | `CHECKSTYLE`         | 
| [_Simian_](http://www.harukizaemon.com/simian/)                                       | `SIMIAN`             | 
| [_Sonar_](https://www.sonarqube.org/)                                                 | `SONAR`              | With `mvn sonar:sonar -Dsonar.analysis.mode=preview -Dsonar.report.export.path=sonar-report.json`. Removed in 7.7, see [SONAR-11670](https://jira.sonarsource.com/browse/SONAR-11670) but can be retrieved with: `curl --silent 'http://sonar-server/api/issues/search?componentKeys=unique-key&resolved=false' \| jq -f sonar-report-builder.jq > sonar-report.json`.
| [_Spotbugs_](https://spotbugs.github.io/)                                             | `FINDBUGS`           | 
| [_StyleCop_](https://stylecop.codeplex.com/)                                          | `STYLECOP`           | 
| [_SwiftLint_](https://github.com/realm/SwiftLint)                                     | `CHECKSTYLE`         | With `--reporter checkstyle`.
| [_TSLint_](https://palantir.github.io/tslint/usage/cli/)                              | `CHECKSTYLE`         | With `-t checkstyle`
| [_XMLLint_](http://xmlsoft.org/xmllint.html)                                          | `XMLLINT`            | 
| [_XUnit_](https://xunit.net/)                                                         | `XUNIT`              | It only contains the failures.
| [_YAMLLint_](https://yamllint.readthedocs.io/en/stable/index.html)                    | `YAMLLINT`           | With `-f parsable`
| [_ZPTLint_](https://pypi.python.org/pypi/zptlint)                                     | `ZPTLINT`            |

Missing a format? Open an issue [here](https://github.com/tomasbjerre/violations-lib/issues)!

There is also:
 * A [Gradle plugin](https://github.com/tomasbjerre/violation-comments-to-gitlab-gradle-plugin).
 * A [Maven plugin](https://github.com/tomasbjerre/violation-comments-to-gitlab-maven-plugin).

## Notify Jenkins from GitLab

* You may trigger with a [webhook](https://docs.gitlab.com/ce/user/project/integrations/webhooks.html#merge-request-events) in GitLab. And consume it with [Generic Webhook Trigger plugin](https://github.com/jenkinsci/generic-webhook-trigger-plugin) to get the variables you need.

* Or, trigger with [GitLab plugin](https://github.com/jenkinsci/gitlab-plugin).

* Or, trigger with [GitLab Merge Request Builder Plugin](https://github.com/timols/jenkins-gitlab-merge-request-builder-plugin).

## Merge

**You must perform the merge before build**. If you don't perform the merge, the reported violations will refer to other lines then those in the pull request. The merge can be done with a shell script like this.

```shell
echo ---
echo --- Merging from $FROM in $FROMREPO to $TO in $TOREPO
echo ---
git clone $TOREPO
cd *
git reset --hard $TO
git status
git remote add from $FROMREPO
git fetch from
git merge $FROM
git --no-pager log --max-count=10 --graph --abbrev-commit

Your build command here!
```

# Screenshots

Comments can be made on the diff with one comment per violation `createSingleFileComments`.

![Example comment on diff](https://github.com/jenkinsci/violation-comments-to-gitlab-plugin/blob/master/sandbox/gitlab-comment-diff.png)

Or one big comment can be made, `createCommentWithAllSingleFileComments`.

![Example comment](https://github.com/jenkinsci/violation-comments-to-gitlab-plugin/blob/master/sandbox/mergerequest-onecomment.png)

## Job DSL Plugin

This plugin can be used with the Job DSL Plugin. Here is an example using [Generic Webhook Trigger plugin](https://github.com/jenkinsci/generic-webhook-trigger-plugin), [HTTP Request Plugin](https://wiki.jenkins-ci.org/display/JENKINS/HTTP+Request+Plugin) and [Conditional BuildStep Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Conditional+BuildStep+Plugin).

```groovy
job('GitLab_MR_Builder') {
 concurrentBuild()
 quietPeriod(0)
 parameters {
  stringParam('MERGE_REQUEST_TO_URL', '')
  stringParam('MERGE_REQUEST_FROM_URL', '')
  stringParam('MERGE_REQUEST_TO_BRANCH', '')
  stringParam('MERGE_REQUEST_FROM_BRANCH', '')
 }
 scm {
  git {
   remote {
    name('origin')
    url('$MERGE_REQUEST_TO_URL')
   }
   remote {
    name('upstream')
    url('$MERGE_REQUEST_FROM_URL')
   }
   branch('$MERGE_REQUEST_FROM_BRANCH')
   extensions {
    mergeOptions {
     remote('upstream')
     branch('$MERGE_REQUEST_TO_BRANCH')
    }
   }
  }
 }
 triggers {
  genericTrigger {
   genericVariables {
    genericVariable {
     key("MERGE_REQUEST_TO_URL")
     value("\$.object_attributes.target.git_http_url")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MERGE_REQUEST_FROM_URL")
     value("\$.object_attributes.source.git_http_url")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MERGE_REQUEST_TO_BRANCH")
     value("\$.object_attributes.target_branch")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MERGE_REQUEST_FROM_BRANCH")
     value("\$.object_attributes.source_branch")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("PROJECT_ID")
     value("\$.object_attributes.target_project_id")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MERGE_REQUST_ID")
     value("\$.object_attributes.id")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MR_OBJECT_KIND")
     value("\$.object_kind")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MR_OLD_REV")
     value("\$.object_attributes.oldrev")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MR_ACTION")
     value("\$.object_attributes.action")
     expressionType("JSONPath")
     regexpFilter("")
    }
   }
   regexpFilterText("\$MR_OBJECT_KIND \$MR_ACTION \$MR_OLD_REV")
   regexpFilterExpression("^merge_request\\s(update\\s.{40}\$|open.*)")
  }
 }
 steps {
  httpRequest {
   url("http://gitlab:880/api/v3/projects/\$PROJECT_ID/merge_requests/\$MERGE_REQUST_ID/notes?private_token=AvAkp6HtUvzpesPypXSk")
   consoleLogResponseBody(true)
   httpMode("POST")
   requestBody('body=Building... %20\$BUILD_URL')
   }
 
  shell('./gradlew build')
 
  conditionalBuilder {
   runCondition {
    statusCondition {
     worstResult('SUCCESS')
     bestResult('SUCCESS')
    }
    runner {
     runUnstable()
    }
    conditionalbuilders {
     httpRequest {
      url('http://gitlab:880/api/v3/projects/\$PROJECT_ID/merge_requests/\$MERGE_REQUST_ID/notes?private_token=AvAkp6HtUvzpesPypXSk')
      consoleLogResponseBody(true)
      httpMode('POST')
      requestBody('body=SUCCESS%20\$BUILD_URL')
     }
    }
   }
  }
 
  conditionalBuilder {
   runCondition {
    statusCondition {
     worstResult('FAILURE')
     bestResult('FAILURE')
    }
    runner {
     runUnstable()
    }
    conditionalbuilders {
     httpRequest {
      url('http://gitlab:880/api/v3/projects/\$PROJECT_ID/merge_requests/\$MERGE_REQUST_ID/notes?private_token=AvAkp6HtUvzpesPypXSk')
      consoleLogResponseBody(true)
      httpMode('POST')
      requestBody('body=FAIL%20\$BUILD_URL')
     }
    }
   }
  }
 }
 publishers {
  violationsToGitLabRecorder {
   config {
    gitLabUrl("http://gitlab:880/")
    projectId("\$PROJECT_ID")
    mergeRequestIid("\$MERGE_REQUST_IID")
    
    // Only specify proxy if you need it
    proxyUri('')
    proxyCredentialsId('') // A username/password credential
 
    commentOnlyChangedContent(true)
    commentOnlyChangedContentContext(0)
    commentOnlyChangedFiles(true)
    createSingleFileComments(true)
    createCommentWithAllSingleFileComments(true)
    minSeverity('INFO')
    maxNumberOfViolations(99999)
    
    //You may want this when troubleshooting things
    enableLogging(true)
    
 
    apiTokenCredentialsId("gitlabtoken") // A secret text credential
    apiTokenPrivate(true)
    authMethodHeader(true)
    ignoreCertificateErrors(true)
    
    commentTemplate("""
    **Reporter**: {{violation.reporter}}{{#violation.rule}}
    
    **Rule**: {{violation.rule}}{{/violation.rule}}
    **Severity**: {{violation.severity}}
    **File**: {{violation.file}} L{{violation.startLine}}{{#violation.source}}
    
    **Source**: {{violation.source}}{{/violation.source}}
    
    {{violation.message}}
    """)
 
    violationConfigs {
     violationConfig {
      parser("FINDBUGS")
      reporter("Findbugs")
      pattern(".*/findbugs/.*\\.xml\$")
     }
     violationConfig {
      parser("CHECKSTYLE")
      reporter("Checkstyle")
      pattern(".*/checkstyle/.*\\.xml\$")
     }
    }
   }
  }
 }
}
```

## Pipeline Plugin

Here is an example pipeline that will merge, run unit tests, run static code analysis and finally report back to GitLab. It requires the [GitLab Plugin](https://github.com/jenkinsci/gitlab-plugin).

```groovy
pipelineJob("merge-request-pipeline") {
 concurrentBuild()
 quietPeriod(0)
 authenticationToken("thetoken")
 triggers {
  genericTrigger {
   genericVariables {
    genericVariable {
     key("MERGE_REQUEST_TO_URL")
     value("\$.object_attributes.target.git_http_url")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MERGE_REQUEST_FROM_URL")
     value("\$.object_attributes.source.git_http_url")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MERGE_REQUEST_TO_BRANCH")
     value("\$.object_attributes.target_branch")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MERGE_REQUEST_FROM_BRANCH")
     value("\$.object_attributes.source_branch")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("PROJECT_ID")
     value("\$.object_attributes.target_project_id")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("PROJECT_PATH")
     value("\$.object_attributes.target.path_with_namespace")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MERGE_REQUST_IID")
     value("\$.object_attributes.iid")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MR_OBJECT_KIND")
     value("\$.object_kind")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MR_OLD_REV")
     value("\$.object_attributes.oldrev")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MR_ACTION")
     value("\$.object_attributes.action")
     expressionType("JSONPath")
     regexpFilter("")
    }
    genericVariable {
     key("MR_TITLE")
     value("\$.object_attributes.title")
     expressionType("JSONPath")
     regexpFilter("")
    }
   }
   regexpFilterText("\$MR_OBJECT_KIND \$MR_ACTION \$MR_OLD_REV")
   regexpFilterExpression("^merge_request\\s(update\\s.{40}\$|open.*)")
  }
 }
 
 definition {
  cps {
   script(readFileFromWorkspace('merge_request_pipeline.pipeline'))
   sandbox()
  }
 }
}
```

And the merge_request_pipeline.pipeline contains
```groovy
def commentMr(projectId, mergeRequestIid, comment) {
 def body = comment
 .replaceAll(" ","%20")
 .replaceAll("/","%2F")
 def project = projectId
 .replaceAll("/","%2F")
 sh "curl http://gitlab:80/api/v4/projects/$project/merge_requests/$mergeRequestIid/notes -H 'PRIVATE-TOKEN: 6xRcmSzPzzEXeS2qqr7R' -X POST -d \"body="+body+"\""
}
  
node {
 deleteDir()
 currentBuild.description = "$MR_TITLE from $MERGE_REQUEST_FROM_BRANCH to $MERGE_REQUEST_TO_BRANCH"
  
 commentMr(env.PROJECT_PATH,env.MERGE_REQUST_IID,"Verifierar $MERGE_REQUEST_FROM_BRANCH... ${env.BUILD_URL}")
  
 stage('Merge') {
  sh "git init"
  sh "git fetch --no-tags $MERGE_REQUEST_TO_URL +refs/heads/*:refs/remotes/origin/* --depth=200"
  sh "git checkout origin/${env.MERGE_REQUEST_TO_BRANCH}"
  sh "git config user.email 'je@nkins.domain'"
  sh "git config user.name 'jenkins'"
  sh "git merge origin/${env.MERGE_REQUEST_FROM_BRANCH}"
  sh "git log --graph --abbrev-commit --max-count=10"
 }
  
 stage('Compile') {
  sh "./gradlew assemble"
 }
  
 stage('Unit test') {
  sh "./gradlew test"
  commentMr(env.PROJECT_PATH,env.MERGE_REQUST_IID,"Test ok in $MERGE_REQUEST_FROM_BRANCH =) ${env.BUILD_URL}")
 }
  
 stage('Regression test') {
  sh "echo regtest"
  commentMr(env.PROJECT_PATH,env.MERGE_REQUST_IID,"Regression test ok in $MERGE_REQUEST_FROM_BRANCH =) ${env.BUILD_URL}")
 }
  
 stage('Static code analysis') {
 sh "./gradlew check"
 step([
 $class: 'ViolationsToGitLabRecorder',
 config: [
  gitLabUrl: 'http://gitlab:80/',
  projectId: env.PROJECT_PATH,
  mergeRequestIid: env.MERGE_REQUST_IID,
  commentOnlyChangedContent: true,
  commentOnlyChangedContentContext: 0,
  commentOnlyChangedFiles: true,
  createSingleFileComments: true,
  createCommentWithAllSingleFileComments: true,
  minSeverity: 'INFO',
  maxNumberOfViolations: 99999,
 
  // You may want this when troubleshooting things
  enableLogging: true,
 
  // Only specify proxy if you need it
  proxyUri: '',
  proxyCredentialsId: '', // A username/password credential
 
  apiTokenCredentialsId: 'id', // A secret text credential
  apiTokenPrivate: true,
  authMethodHeader: true,
  ignoreCertificateErrors: true,
  keepOldComments: false,
  shouldSetWip: true,
 
  commentTemplate: """
 **Reporter**: {{violation.reporter}}{{#violation.rule}}
 
 **Rule**: {{violation.rule}}{{/violation.rule}}
 **Severity**: {{violation.severity}}
 **File**: {{violation.file}} L{{violation.startLine}}{{#violation.source}}
 
 **Source**: {{violation.source}}{{/violation.source}}
 
 {{violation.message}}
  """,
 
  violationConfigs: [
   [ pattern: '.*/checkstyle/.*\\.xml$', parser: 'CHECKSTYLE', reporter: 'Checkstyle' ],
   [ pattern: '.*/findbugs/.*\\.xml$', parser: 'FINDBUGS', reporter: 'Findbugs' ],
   [ pattern: '.*/pmd/.*\\.xml$', parser: 'PMD', reporter: 'PMD' ],
  ]
 ]
 ])
 }
}
```

# Plugin development
More details on Jenkins plugin development is available [here](https://wiki.jenkins-ci.org/display/JENKINS/Plugin+tutorial).

There is a ```/build.sh``` that will perform a full build and test the plugin.

If you have release-permissions this is how you do a release:

```
mvn release:prepare release:perform
```
