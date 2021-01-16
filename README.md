# [JENKINS-34042](https://issues.jenkins.io/browse/JENKINS-34042) Empty exception message on checkout failure

When bad arguments are passed to the checkout step, the user would like
a non-empty exception message to be referenced in the catch block.
