# [JENKINS-55284](https://issues.jenkins.io/browse/JENKINS-55284) - updated tags not pulled by CLI git 2.20 and later

Updating a tag on a shared repository is specifically advised against
in the command line git man page for 'tag'.  However, git versions
prior to 2.20 would silently update a local tag when the remote tag had
been changed.  That bug was fixed in git 2.20.  Some Jenkins users have
depended on the bad behavior of command line git and rely on the Jenkins
job to continue behaving that way.

A change in git client plugin 2.7.5 attempted to adapt to that, but two
users reported that the attempt was unsuccessful.  Tags updated on the
shared repository are not being pulled into the Jenkins workspace when
using command line git 2.20.
