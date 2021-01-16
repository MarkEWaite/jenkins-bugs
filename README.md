# [JENKINS-39968](https://issues.jenkins.io/browse/JENKINS-39968) checkout returns same value on subsequent calls

Two calls to checkout in a Pipeline returned the same values in the map,
even though the two checkout steps were distinct commits on different
repositories.
