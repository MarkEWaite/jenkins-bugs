# [JENKINS-22547](https://issues.jenkins-ci.org/browse/JENKINS-22547) checkout timeout ignored

The option for checkout timeout is ignored in the git plugin.  The setting
is accepted and persists with the job, but then the job does not use it.

Job will fail in a pipeline build until the pipeline definition sets
the checkout timeout and the pipeline build process honors the checkout
timeout setting.

Job should succeed in a non-pipeline job after the fix is made, if that
non-pipeline job is configured to set a checkout timeout of 37.

This is probably the type of bug check that is best done in a separate
freestyle job, without including it in the jenkins-bugs repository as
a branch.
