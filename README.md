# [JENKINS-35475](https://issues.jenkins-ci.org/browse/JENKINS-35475) Multi-branch pipieline displays SCM view multiple times

When using extended SCM configuration with multibranch pipeline plugin
then SCM views, all the links and revision info is shown twice on the
build view.

If using the configuration as the following one
```
stage('Checkout') {
  checkout scm
}
```
then SCM information is displayed only once.