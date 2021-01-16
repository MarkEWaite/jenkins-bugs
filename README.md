# [JENKINS-52059](https://issues.jenkins.io/browse/JENKINS-52059) scripted pipeline checkout scm does not populate environment

The scripted pipeline checkout step does not populate the environment
with GIT_* values like GIT_COMMIT.  It returns them from the checkout
step in a map of names and their values.
