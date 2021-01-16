# [JENKINS-22795](https://issues.jenkins.io/browse/JENKINS-22795) - files created before checkout are deleted

Files created before the checkout step (in a pre-scm step, or before the
checkout step in pipeline) are deleted by the git plugin checkout step.
