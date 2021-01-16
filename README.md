# [JENKINS-30515](https://issues.jenkins.io/browse/JENKINS-30515) Missing credentials not clearly reported

When incorrect credentials are provided to checkout the error message
is not clear. It reports a stack trace instead of reporting that the
credentials could not be found.

Some of the cases to test include:

* Non-existent credentials to a public https repository
* Non-existent credentials to a public ssh repository
* Non-existent credentials to a private https repository
* Non-existent credentials to a private ssh repository
* Incorrect credentials to a private https repository
* Incorrect credentials to a private ssh repository
