# [JENKINS-50394](https://issues.jenkins.io/browse/JENKINS-50394) missing object ID exception

Commits that arrive in the remote repository during branch indexing can be detected by the indexing REST API call.
If they arrived in the remote repo after local clone started, they may not be included in the clone.
