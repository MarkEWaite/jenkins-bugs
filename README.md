# [JENKINS-50168](https://issues.jenkins.io/browse/JENKINS-50168) Parameterized Pipeline job polls last built branch instead of default branch

## Build Trigger: "Poll SCM"

* Parametrized with String parameter "BRANCH", default value "JENKINS-50168"
* Definition is "Pipeline script from SCM" with Branch Specifier "$BRANCH", "Lightweight checkout" unchecked

## Expected behaviour:

The job polls SCM. When changes are detected in the branch specified in the default value of the BRANCH parameter ("JENKINS-50168" in this case), the job is triggered.

## Actual behaviour:

The job polls SCM, but it ONLY triggers the job when changes are detected in the branch that was last built.

## This causes the following issue:

After I manually trigger the job with branch "master", subsequent changes in branch "JENKINS-50168" are ignored.
