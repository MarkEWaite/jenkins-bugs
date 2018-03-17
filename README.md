# [JENKINS-50168](https://issues.jenkins-ci.org/browse/JENKINS-50168) Parameterized Pipeline job polls last built branch instead of default branch

## Build Trigger: "Poll SCM"

* Parametrized with String parameter "BRANCH", default value "master"
* Definition is "Pipeline script from SCM" with Branch Specifier "$BRANCH", "Lightweight checkout" unchecked

## Expected behaviour:

The job polls SCM. When changes are detected in the branch specified in the default value of the BRANCH parameter ("master" in this case), the job is triggered.

## Actual behaviour:

The job polls SCM, but it ONLY triggers the job when changes are detected in the branch that was last built.

## This causes the following issue:

After I manually trigger the job with branch "develop", subsequent changes in branch "master" are ignored.
