# Violation Comments to GitLab Plugin

[![Build Status](https://jenkins.ci.cloudbees.com/job/plugins/job/violation-comments-to-gitlab-plugin/badge/icon)](https://jenkins.ci.cloudbees.com/job/plugins/job/violation-comments-to-gitlab-plugin/)

This is a Jenkins plugin for [Violation Comments to GitLab Lib](https://github.com/tomasbjerre/violation-comments-to-gitlab-lib). This plugin will find report files from static code analysis and comment GitLab pull requests with the content.

You can have a look at [violations-test](https://gitlab.com/tomas.bjerre85/violations-test/merge_requests/1) to see what the result may look like.

Available in Jenkins [here](https://wiki.jenkins-ci.org/display/JENKINS/Violation+Comments+to+GitLab+Plugin).

You can get the variables you need from the [GitLab plugin](https://github.com/jenkinsci/gitlab-plugin) or [GitLab Merge Request Builder Plugin](https://github.com/timols/jenkins-gitlab-merge-request-builder-plugin).

It supports:
 * [_AndoidLint_](http://developer.android.com/tools/help/lint.html)
 * [_Checkstyle_](http://checkstyle.sourceforge.net/)
  * [_ESLint_](https://github.com/sindresorhus/grunt-eslint) with `format: 'checkstyle'`.
  * [_PHPCS_](https://github.com/squizlabs/PHP_CodeSniffer) with `phpcs api.php --report=checkstyle`.
 * [_CLang_](https://clang-analyzer.llvm.org/)
  * [_RubyCop_](http://rubocop.readthedocs.io/en/latest/formatters/) with `rubycop -f clang file.rb`
 * [_CodeNarc_](http://codenarc.sourceforge.net/)
 * [_CPD_](http://pmd.sourceforge.net/pmd-4.3.0/cpd.html)
 * [_CPPLint_](https://github.com/theandrewdavis/cpplint)
 * [_CPPCheck_](http://cppcheck.sourceforge.net/)
 * [_CSSLint_](https://github.com/CSSLint/csslint)
 * [_Findbugs_](http://findbugs.sourceforge.net/)
 * [_Flake8_](http://flake8.readthedocs.org/en/latest/)
  * [_Pep8_](https://github.com/PyCQA/pycodestyle)
  * [_Mccabe_](https://pypi.python.org/pypi/mccabe)
  * [_PyFlakes_](https://pypi.python.org/pypi/pyflakes)
 * [_FxCop_](https://en.wikipedia.org/wiki/FxCop)
 * [_Gendarme_](http://www.mono-project.com/docs/tools+libraries/tools/gendarme/)
 * [_GoLint_](https://github.com/golang/lint)
  * [_GoVet_](https://golang.org/cmd/vet/) Same format as GoLint.
 * [_JSHint_](http://jshint.com/)
 * _Lint_ A common XML format, used by different linters.
 * [_JCReport_](https://github.com/jCoderZ/fawkez/wiki/JcReport)
 * [_MyPy_](https://pypi.python.org/pypi/mypy-lang)
 * [_PerlCritic_](https://github.com/Perl-Critic)
 * [_PiTest_](http://pitest.org/)
 * [_PyDocStyle_](https://pypi.python.org/pypi/pydocstyle)
 * [_PyLint_](https://www.pylint.org/)
 * [_PMD_](https://pmd.github.io/)
  * [_PHPPMD_](https://phpmd.org/) with `phpmd api.php xml ruleset.xml`.
 * [_ReSharper_](https://www.jetbrains.com/resharper/)
 * [_Simian_](http://www.harukizaemon.com/simian/)
 * [_StyleCop_](https://stylecop.codeplex.com/)
 * [_XMLLint_](http://xmlsoft.org/xmllint.html)
 * [_ZPTLint_](https://pypi.python.org/pypi/zptlint)


# Screenshots

![Example comment](https://github.com/jenkinsci/violation-comments-to-gitlab-plugin/blob/master/sandbox/mergerequest-onecomment.png)

## Job DSL Plugin

This plugin can be used with the Job DSL Plugin.

```
job('example') {
 publishers {
  violationsToGitLabRecorder {
   config {
    gitLabUrl("https://gitlab.com/")
    projectId(123)
    mergeRequestId(456)

    commentOnlyChangedContent(true)
    createCommentWithAllSingleFileComments(true)

    useApiToken(true)
    apiToken("")
    useApiTokenCredentials(false)
    apiTokenCredentialsId("id")
    apiTokenPrivate(true)
    authMethodHeader(true)
    ignoreCertificateErrors(true)

    violationConfigs {
     violationConfig {
      reporter("FINDBUGS")
      pattern(".*/findbugs/.*\\.xml\$")
     }
    }
   }
  }
 }
}
```

## Pipeline Plugin

This plugin can be used with the Pipeline Plugin:

```
node {

 checkout([
  $class: 'GitSCM', 
  branches: [[ name: '*/master' ]], 
  doGenerateSubmoduleConfigurations: false,
  extensions: [],
  submoduleCfg: [],
  userRemoteConfigs: [[ url: 'https://gitlab.com/tomas.bjerre85/violations-test.git' ]]
 ])

 sh '''
 ./gradlew build
 '''

 step([
  $class: 'ViolationsToGitLabRecorder', 
  config: [
   gitLabUrl: 'https://gitlab.com/',
   projectId: 123,
   mergeRequestId: 456,

   commentOnlyChangedContent: true,
   createCommentWithAllSingleFileComments: true,

   useApiToken: true,
   apiToken: '',
   useApiTokenCredentials: false,
   apiTokenCredentialsId: 'id',
   apiTokenPrivate: true,
   authMethodHeader: true,
   ignoreCertificateErrors: true,

   violationConfigs: [
    [ pattern: '.*/checkstyle/.*\\.xml$', reporter: 'CHECKSTYLE' ], 
    [ pattern: '.*/findbugs/.*\\.xml$', reporter: 'FINDBUGS' ], 
   ]
  ]
 ])
}
```

# Plugin development
More details on Jenkins plugin development is available [here](https://wiki.jenkins-ci.org/display/JENKINS/Plugin+tutorial).

There is a ```/build.sh``` that will perform a full build and test the plugin. You may have a look at sandbox/settings.xml on how to configure your Maven settings.

A release is created like this. You need to clone from jenkinsci-repo, with https and have username/password in settings.xml.
```
mvn release:prepare release:perform
```
