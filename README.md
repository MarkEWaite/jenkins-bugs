# [JENKINS-50556](https://issues.jenkins-ci.org/browse/JENKINS-50556) Polling incorrectly reports changes

When building with a local branch checkout there are cases where changes
are detected even though there were no changes.

This branch intentionally does not checkout to the local branch.
