# [JENKINS-52746](https://issues.jenkins-ci.org/browse/JENKINS-52746) - polling always finds changes if deleteDir used as a post action

A declarative Pipeline that polls will always detect changes if the
deleteDir step is used in the post block.