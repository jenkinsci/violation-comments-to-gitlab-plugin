# Violation Comments to GitLab Plugin

[![Build Status](https://ci.jenkins.io/job/Plugins/job/violation-comments-to-gitlab-plugin/job/master/badge/icon)](https://ci.jenkins.io/job/Plugins/job/violation-comments-to-gitlab-plugin)

This is a Jenkins plugin for [Violation Comments to GitLab Lib](https://github.com/tomasbjerre/violation-comments-to-gitlab-lib). This plugin will find report files from static code analysis and comment GitLab pull requests with the content.

You can have a look at [violations-test](https://gitlab.com/tomas.bjerre85/violations-test/merge_requests/1) to see what the result may look like.

Available in Jenkins [here](https://wiki.jenkins-ci.org/display/JENKINS/Violation+Comments+to+GitLab+Plugin).

It supports:
 * [_AndroidLint_](http://developer.android.com/tools/help/lint.html)
 * [_Checkstyle_](http://checkstyle.sourceforge.net/)
   * [_Detekt_](https://github.com/arturbosch/detekt) with `--output-format xml`.
   * [_ESLint_](https://github.com/sindresorhus/grunt-eslint) with `format: 'checkstyle'`.
   * [_KTLint_](https://github.com/shyiko/ktlint)
   * [_SwiftLint_](https://github.com/realm/SwiftLint) with `--reporter checkstyle`.
   * [_PHPCS_](https://github.com/squizlabs/PHP_CodeSniffer) with `phpcs api.php --report=checkstyle`.
 * [_CLang_](https://clang-analyzer.llvm.org/)
   * [_RubyCop_](http://rubocop.readthedocs.io/en/latest/formatters/) with `rubycop -f clang file.rb`
 * [_CodeNarc_](http://codenarc.sourceforge.net/)
 * [_CPD_](http://pmd.sourceforge.net/pmd-4.3.0/cpd.html)
 * [_CPPLint_](https://github.com/theandrewdavis/cpplint)
 * [_CPPCheck_](http://cppcheck.sourceforge.net/)
 * [_CSSLint_](https://github.com/CSSLint/csslint)
 * [_DocFX_](http://dotnet.github.io/docfx/)
 * [_Findbugs_](http://findbugs.sourceforge.net/)
 * [_Flake8_](http://flake8.readthedocs.org/en/latest/)
   * [_AnsibleLint_](https://github.com/willthames/ansible-lint) with `-p`
   * [_Mccabe_](https://pypi.python.org/pypi/mccabe)
   * [_Pep8_](https://github.com/PyCQA/pycodestyle)
   * [_PyFlakes_](https://pypi.python.org/pypi/pyflakes)
 * [_FxCop_](https://en.wikipedia.org/wiki/FxCop)
 * [_Gendarme_](http://www.mono-project.com/docs/tools+libraries/tools/gendarme/)
 * [_GoLint_](https://github.com/golang/lint)
   * [_GoVet_](https://golang.org/cmd/vet/) Same format as GoLint.
 * [_GoogleErrorProne_](https://github.com/google/error-prone)
   * [_NullAway_](https://github.com/uber/NullAway) Same format as Google Error Prone.
 * [_JSHint_](http://jshint.com/)
 * _Lint_ A common XML format, used by different linters.
 * [_JCReport_](https://github.com/jCoderZ/fawkez/wiki/JcReport)
 * [_Klocwork_](http://www.klocwork.com/products-services/klocwork/static-code-analysis)
 * [_MyPy_](https://pypi.python.org/pypi/mypy-lang)
 * [_PCLint_](http://www.gimpel.com/html/pcl.htm) PC-Lint using the same output format as the Jenkins warnings plugin, [_details here_](https://wiki.jenkins.io/display/JENKINS/PcLint+options)
 * [_PerlCritic_](https://github.com/Perl-Critic)
 * [_PiTest_](http://pitest.org/)
 * [_PyDocStyle_](https://pypi.python.org/pypi/pydocstyle)
 * [_PyLint_](https://www.pylint.org/)
 * [_PMD_](https://pmd.github.io/)
   * [_Infer_](http://fbinfer.com/) Facebook Infer. With `--pmd-xml`.
   * [_PHPPMD_](https://phpmd.org/) with `phpmd api.php xml ruleset.xml`.
 * [_ReSharper_](https://www.jetbrains.com/resharper/)
 * [_SbtScalac_](http://www.scala-sbt.org/)
 * [_Simian_](http://www.harukizaemon.com/simian/)
 * [_StyleCop_](https://stylecop.codeplex.com/)
 * [_XMLLint_](http://xmlsoft.org/xmllint.html)
 * [_ZPTLint_](https://pypi.python.org/pypi/zptlint)


There is also:
 * A [Gradle plugin](https://github.com/tomasbjerre/violation-comments-to-gitlab-gradle-plugin).
 * A [Maven plugin](https://github.com/tomasbjerre/violation-comments-to-gitlab-maven-plugin).

## Notify Jenkins from GitLab

* You may trigger with a [webhook](https://docs.gitlab.com/ce/user/project/integrations/webhooks.html#merge-request-events) in GitLab. And consume it with [Generic Webhook Trigger plugin](https://github.com/jenkinsci/generic-webhook-trigger-plugin) to get the variables you need.

* Or, trigger with [GitLab plugin](https://github.com/jenkinsci/gitlab-plugin).

* Or, trigger with [GitLab Merge Request Builder Plugin](https://github.com/timols/jenkins-gitlab-merge-request-builder-plugin).

## Merge

**You must perform the merge before build**. If you don't perform the merge, the reported violations will refer to other lines then those in the pull request. The merge can be done with a shell script like this.

```
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

![Example comment](https://github.com/jenkinsci/violation-comments-to-gitlab-plugin/blob/master/sandbox/mergerequest-onecomment.png)

## Job DSL Plugin

This plugin can be used with the Job DSL Plugin. Here is an example using [Generic Webhook Trigger plugin](https://github.com/jenkinsci/generic-webhook-trigger-plugin), [HTTP Request Plugin](https://wiki.jenkins-ci.org/display/JENKINS/HTTP+Request+Plugin) and [Conditional BuildStep Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Conditional+BuildStep+Plugin).

```
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
    mergeRequestId("\$MERGE_REQUST_ID")
 
    commentOnlyChangedContent(true)
    createCommentWithAllSingleFileComments(true)
    minSeverity('INFO')
 
    apiToken("")
    apiTokenCredentialsId("gitlabtoken")
    apiTokenPrivate(true)
    authMethodHeader(true)
    ignoreCertificateErrors(true)
 
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

```
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
```
def commentMr(projectId, mergeRequestId, comment) {def body = comment
 .replaceAll(" ","%20")
 .replaceAll("/","%2F")
 def project = projectId
 .replaceAll("/","%2F")
 sh "curl http://gitlab:80/api/v4/projects/$project/merge_requests/$mergeRequestId/notes -H 'PRIVATE-TOKEN: 6xRcmSzPzzEXeS2qqr7R' -X POST -d \"body="+body+"\""
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
 mergeRequestId: env.MERGE_REQUST_IID,
 commentOnlyChangedContent: true,
 createCommentWithAllSingleFileComments: true,
 minSeverity: 'INFO',
 
 //Specify one of these
 apiToken: '6xRcmSzPzzEXeS2qqr7R',
 apiTokenCredentialsId: 'id',
 
 apiTokenPrivate: true,
 authMethodHeader: true,
 ignoreCertificateErrors: true,
 keepOldComments: false,
 shouldSetWip: true,
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
