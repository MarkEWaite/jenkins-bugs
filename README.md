# [JENKINS-52746](https://issues.jenkins-ci.org/browse/JENKINS-52746) - polling always finds changes if deleteDir used as a post action

A declarative Pipeline that polls will always detect changes if the
deleteDir step is used in the post block.

This test job only works in a multibranch Pipeline, not in a Pipeline that is not multibranch.
[JENKINS-47226](https://issues.jenkins-ci.org/browse/JENKINS-47226) describes the reason it does not work in Pipeline.
The workaround in that bug report is used in this Jenkinsfile as well.
