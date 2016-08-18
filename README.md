# [JENKINS-37263](https://issues.jenkins-ci.org/browse/JENKINS-37263) - LocalBranch extension causes silent checkout of wrong SHA1

When the LocalBranch extension is used, it (correctly) creates a
local branch.  Unfortunately, the local branch created (correctly) matches
the branch name being tracked for changes.  When polling or notifyCommit
detects a change, the change is seen and a build is started, but then the
matching code detects that there are multiple matches to the branch name
(the local branch and the remote branch).
It seems to choose one of them, and often seems to choose exactly the wrong
one.
