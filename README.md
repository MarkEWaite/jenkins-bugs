# [JENKINS-66651](https://issues.jenkins.io/browse/JENKINS-66651) GIT_BRANCH not expanded by `tm` pipeline task

The `tm` pipeline task says that it expands the token macro that is provided as an argument.
It works very well for the '${BUILD_NUMBER}' token macro.
It does not work for the `GIT_BRANCH` token macro.

Evaluation of `GIT_BRANCH` always reports `GIT_BRANCH is not supported in this context`.
