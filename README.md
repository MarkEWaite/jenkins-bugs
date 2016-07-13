# [JENKINS-36637](https://issues.jenkins-ci.org/browse/JENKINS-36637) change list incomplete with some characters

This branch shows that certain characters damage the git plugin changelog.

The job also starts twice for a single commit.  I assume that is due to a
race condition in the polling code (two polls started by the notifyCommit
from the cache repo very quickly).
