# [JENKINS-59016](https://issues.jenkins.io/browse/JENKINS-59016) Folder scoped credential not used for GitUHub branch scan

A credential defined in a folder (and not at the root level) is not
used by the GitHub branch source plugin when scanning for repositories
with GitHub branch source 2.5.6.  When using GitHub branch source 2.3.6,
the scanning for repositories uses the credential.
