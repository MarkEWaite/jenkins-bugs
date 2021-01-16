# [JENKINS-55939](https://issues.jenkins.io/browse/JENKINS-55939) REST API incompatible in 4.0.0-rc

## REST API incompatible with the JENKINS-19022 fix

The `lastBuiltRevision` field was removed to be replaced by an instance of `BuildDetails`.  Unfortunately, many tools depend on that REST API remaining consistent.  There were no automated tests of the REST API, so this acceptance test will check the REST API continues to be well-behaved.

## Expected behaviour:

Expected values are returned from the REST API calls.
