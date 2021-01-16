# [JENKINS-45894](https://issues.jenkins.io/browse/JENKINS-45894) array index bounds exception in branch list

Special branch names cause an array bounds exception in the git client
plugin when they encounter unexpected output from the "git branch"
command.

One of the comments in the bug report said that a branch name which
contains a "fullstop" caused the issue.  Unicode "full stop" is ASCII 2E,
the "period".  This branch name includes ASCII 2E in its name.
