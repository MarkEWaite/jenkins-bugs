# [JENKINS-60564](https://issues.jenkins.io/browse/JENKINS-60564) Optional rebase before push

Allow the result of a merge to be pushed to the origin repository even if remote no longer matches the local merge base.
Optional checkbox allows a rebase before push.
User accepts that if they enable rebase before push, they are pushing something that was tested in a different environment.
