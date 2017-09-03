# Violation Comments to GitLab changelog
Changelog of Violation Comments to GitLab plugin for Jenkins.
## Unreleased
### No issue

**Doc**


[df0fa3d58e2b178](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/df0fa3d58e2b1782a334e875d97ca7672567091c) Tomas Bjerre *2017-09-03 08:13:26*


## 1.12
### No issue

**Keeping comments and adjusting checkstyle**

 * Checkstyle now allows empty source attribute. 
 * Comments can optionaly be kept and not removed when new comments are added. 
 * Will no longer re-create identical comments. 
 * Using API V4 

[3ec3b9a97663a01](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/3ec3b9a97663a019c7daa15342d98e0d8283cc63) Tomas Bjerre *2017-09-02 14:49:39*

**Keeping comments and adjusting checkstyle**

 * Checkstyle now allows empty source attribute. 
 * Comments can optionaly be kept and not removed when new comments are added. 
 * Will no longer re-create identical comments. 
 * Using GitLab API V4 

[5780a2457e06631](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/5780a2457e066310f164985b8666ccd9fcf68321) Tomas Bjerre *2017-09-02 09:30:44*


## 1.11
### No issue

**Ignoring violation configs with null config**


[5f8c94518aeaf9a](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/5f8c94518aeaf9a3d3207ff4aa3bcbd93baa47d0) Tomas Bjerre *2017-08-11 11:36:58*


## 1.10
### No issue

**Ignoring violation configs with null config**


[b6720937a4c1e04](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/b6720937a4c1e04881acc590751843d3d7a27348) Tomas Bjerre *2017-08-11 11:35:05*

**Cleaning**


[8db36c5d2049ae4](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/8db36c5d2049ae4ee89e950566a7b1d9708e5fbc) Tomas Bjerre *2017-07-20 19:33:33*

**doc**


[cf45a5aa041e3c8](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/cf45a5aa041e3c848aa0660b0671e5cd4a1ed638) Tomas Bjerre *2017-07-15 13:11:30*


## 1.9
### No issue

**Bugfix, were unable to save reporter from GUI**


[ad8703d0989ca57](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/ad8703d0989ca57cebf176db9a494269b3ac8a76) Tomas Bjerre *2017-07-15 13:09:22*


## 1.8
### No issue

**Violations-lib 1.29 with reporter field**

 * This breaks backwards compatibilty a bit. Reporter is renamed to Parser and an optional text-field called reporter is introduced. If something like Detekt is used to produce a checkstyle-report, then reporter can be set to Detekt and the parser set to CHECKSTYLE. 

[820577dd8dc4546](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/820577dd8dc454615cce51a9168b2ba75e87266f) Tomas Bjerre *2017-07-15 07:54:40*

**Updating doc on Infer**


[93bc67d232b2521](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/93bc67d232b25219cb374ddbfb73262b78e911f1) Tomas Bjerre *2017-06-23 12:53:38*

**doc**


[1869d23af48a0f1](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/1869d23af48a0f1941e42d4f26bbbf11ae93f90d) Tomas Bjerre *2017-06-13 18:01:27*

**doc**


[1f06fa98af3d4b4](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/1f06fa98af3d4b46543a87562cd81d623279f4f4) Tomas Bjerre *2017-04-22 04:32:55*


## 1.7
### No issue

**Allow String as projectId**


[318bde3205e8ed6](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/318bde3205e8ed62f4e37844686fd35fd4ccd93e) Tomas Bjerre *2017-04-21 17:00:23*


## 1.6
### No issue

**URL in Klocwork**


[8f123582ea0373f](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/8f123582ea0373f9237d782c4e685a64d8280559) Tomas Bjerre *2017-04-11 18:29:45*

**doc**


[4e252dd22d077f8](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/4e252dd22d077f8ef7fa079fac916d6b65506ed2) Tomas Bjerre *2017-04-10 20:17:24*


## 1.5
### No issue

**SbtScalac**


[895fe8717e5a255](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/895fe8717e5a255b69e2b9812f329a78d0bcf0dc) Tomas Bjerre *2017-04-10 18:42:20*

**doc**


[38c25c7f2d22f4e](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/38c25c7f2d22f4ebcef8a85be4eea3c388e3e2bb) Tomas Bjerre *2017-04-10 17:31:51*


## 1.4
### No issue

**Klocwork parser**


[03d28f8442fd2cd](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/03d28f8442fd2cd49cafaaf4898596a4daac9d18) Tomas Bjerre *2017-03-30 17:39:30*

**doc**


[2d9ed88aac0ab80](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/2d9ed88aac0ab8056720ef075fc1bf3ac8372c9a) Tomas Bjerre *2017-03-18 19:25:05*


## 1.3
### No issue

**Persisting severity filter on global config**


[222665cc5b92adb](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/222665cc5b92adb16208d89f61d4f5e9a68e9129) Tomas Bjerre *2017-03-17 20:57:23*


## 1.2
### No issue

**Allow filtering on severity**

 * And reducing logging in server log. 

[f2438c25e94162b](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/f2438c25e94162bb10875d67bd53737a5a4a544c) Tomas Bjerre *2017-03-17 19:44:18*

**doc**


[7e25d0de1f34d17](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/7e25d0de1f34d170b739908ce33cd5fdbfd93fdd) Tomas Bjerre *2017-03-14 18:31:23*

**Jenkinsfile**


[b8e058e54415664](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/b8e058e544156641809e94022fabcb2e0fd66070) Tomas Bjerre *2017-03-10 17:38:13*


## 1.1
### No issue

**doc**


[5767ae9c726bf18](https://github.com/repos/jenkinsci/violation-comments-to-gitlab-plugin/5767ae9c726bf18ad2390130ad21213462b14241) Tomas Bjerre *2017-03-04 09:25:07*


