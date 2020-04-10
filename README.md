# [JENKINS-40529](https://issues.jenkins-ci.org/browse/JENKINS-40529) - prune stale tags

Tags in the local workspace which do not exist in the remote repository may be removed
during fetch by the "prune stale tags" extension.  Test that extension.
