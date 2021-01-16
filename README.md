# [JENKINS-53346](https://issues.jenkins.io/browse/JENKINS-53346) checkout returns same value on subsequent calls

Two calls to checkout in a Pipeline returned the same values in the
map, even though the two checkout steps were distinct commits.