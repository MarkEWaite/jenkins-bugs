# [JENKINS-56176](https://issues.jenkins.io/browse/JENKINS-56176) GIT_REVISION not available in git plugin 4.0.0-rc

The GIT_REVISION variable that was available with git plugin 3
and earlier is not available with git plugin 4.0.0-rc.

User created a Freestyle job and assigned the build name as a
post build action with the build name setter plugin. It works
with git plugin 3 and fails with git plugin 4.
