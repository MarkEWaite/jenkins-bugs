# [JENKINS-65183](https://issues.jenkins.io/browse/JENKINS-65183) git step ignores default remote branch

The Jenkins Pipeline git step assumes the remote default branch is 'master' even though that may not be the remote default branch name.
