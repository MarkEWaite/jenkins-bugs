# [JENKINS-42882](https://issues.jenkins-ci.org/browse/JENKINS-42882) symoblic link Jenkinsfile does not resolve

This branch shows that if Jenkinsfile is a symbolic to a file
elsewhere in the repository, the Jenkins pipeline will not resolve the
symbolic link, but will instead read the symbolic link.

The ant task "increment" toggles between having a real Jenkinsfile at
the root directory, and having a symbolic link Jenkinsfile at the root
directory.