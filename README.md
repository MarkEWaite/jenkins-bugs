# [JENKINS-32174](https://issues.jenkins-ci.org/browse/JENKINS-32174) - notifyCommit ignores branches with slashes

A notifyCommit with a branches argument value containing the '/' character
is ignored.  The build does not start when it was expected to start.

This branch intentionally does not contain a slash in its name so that
it can be used for Freestyle job tests.
