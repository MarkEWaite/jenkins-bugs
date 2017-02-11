# [JENKINS-41906](https://issues.jenkins-ci.org/browse/JENKINS-41906) web hook builds an unchanged master

The master branch of the jenkins-bugs repository generally has very few
commits.  It serves as a starting point for new bug verification jobs,
but doesn't have many changes of its own.

JENKINS-41906 verification relies on that low rate of change and checks
that the master branch reports commits within the last 15 minutes
if it builds.  This branch includes the scripts used to check the
master branch, and an "increment" task which creates a commit to the
JENKINS-41906 branch.  If the master branch builds due to a change on
the JENKINS-41906 branch, that would show the bug exists as reported
in JENKINS-41906.
