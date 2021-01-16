# [JENKINS-37263](https://issues.jenkins.io/browse/JENKINS-37263) - LocalBranch extension causes silent checkout of wrong SHA1

When the LocalBranch extension is used, it (correctly) creates a local
branch.  Unfortunately, the local branch created (correctly) matches
the branch name being tracked for changes.  When polling or notifyCommit
detects a change, the change is seen and a build is started, but then the
matching code detects that there are multiple matches to the branch name
(the local branch and the remote branch).  It seems to choose one of them,
and often seems to choose exactly the wrong one.

The first clone into a repository has the correct
SHA1. If that is the case, then "Wipe workspace" provides a work around,
since it will completely recreate the workspace for each job.  That is
not an attractive work-around, but it may be enough for short term use.
When I remove the "wipe workspace" additional behavior from the pipeline
job definition, the problem is again visible.
