# [JENKINS-22547](https://issues.jenkins-ci.org/browse/JENKINS-22547) checkout timeout ignored

The option for checkout timeout is ignored in the git plugin.  The setting
is accepted and persists with the job, but then the job does not use it.
