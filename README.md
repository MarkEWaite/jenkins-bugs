# [JENKINS-59016](https://issues.jenkins-ci.org/browse/JENKINS-59016) Folder scoped credential not used for GitUHub branch scan

A credential defined in a folder (and not at the root level) is not used
by the GitHub branch source plugin when scanning for repositories.
