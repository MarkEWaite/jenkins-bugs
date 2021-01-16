# [JENKINS-32174](https://issues.jenkins.io/browse/JENKINS-32174) - notifyCommit ignores branches with slashes

A notifyCommit with a branches argument value containing the '/' character
is ignored.  The build does not start when it was expected to start.
