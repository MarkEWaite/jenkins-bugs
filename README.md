# [JENKINS-34350](https://issues.jenkins.io/browse/JENKINS-34350) notifyCommit fails POST if CSRF defense is enabled

The notifyCommit HTTP request fails if POST is used and CSRF defense
is enabled.  It works correctly if GET is used, and fails if POST is used.
