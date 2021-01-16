# [JENKINS-35475](https://issues.jenkins.io/browse/JENKINS-35475) Multi-branch pipieline displays SCM view multiple times

When using extended SCM configuration with multibranch pipeline plugin
then SCM views, all the links and revision info is shown twice on the
build view.

If using a configuration like the following:
```
node {
  stage('Checkout') {
    checkout scm
  }
}
```
then SCM information is displayed only once.

In this check job, it depends on my global pipeline library for the
build step and the assert step.  Due to that dependency, one git build
data entry is shown for the pipeline library and one git build data
entry is shown for this branch and its repository.
