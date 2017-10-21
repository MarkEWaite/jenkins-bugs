# [JENKINS-29603](https://issues.jenkins-ci.org/browse/JENKINS-29603) branch names with slashes ignored by notifyCommit

Rather than poll a repository periodically, the repository can notify
the Jenkins server of commits so that the Jenkins server can poll
the repository immediately.  That notification is done with a GET to
/git/notifyCommit with a url parameter.  If an optional branches parameter
is provided, then the specified branches should be checked for changes.

The bug was that a simple job monitoring a branch with a slash in
the branch name, like 'feature/my-things', would be ignored.  If the
job monitored multiple branches, even if they were alternate ways of
describing the same branch, then the problem would not be detected.

Pipeline jobs do not have the problem, since they monitor without heeding
the branches= parameter.
