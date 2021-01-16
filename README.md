# [JENKINS-52855](https://issues.jenkins.io/browse/JENKINS-52855) Add GIT_CHECKOUT_DIR env var

When a Freestyle job performs a checkout to a subdirectory the
containing build script needs to make guesses about the name of
that directory. Make it easier for containing scripts by adding the
GIT_CHECKOUT_DIR environment variable when a Freestyle job performs a
checkout to a subdirectory.

Pipeline jobs and multibranch Pipeline jobs should not use the git
plugin's checkout to a subdirectory facility. They should instead use
the Pipeline steps which will perform the same type of operation within
a Pipeline. For example, they should use the `dir` step or the `ws` step.
