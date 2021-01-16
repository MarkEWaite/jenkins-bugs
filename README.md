# [JENKINS-50556](https://issues.jenkins.io/browse/JENKINS-50556) Polling incorrectly reports changes

When building with workspace polling and a local branch checkout there
are cases where changes are detected even though there were no changes.

Note that this repository must push to origin, without pushing to cache
or bare so that the polling is what detects changes.  If the push happens
to cache then the notifyCommit may be invoked and may hide the issue.
