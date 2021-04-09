# [JENKINS-61317](https://issues.jenkins.io/browse/JENKINS-61317) Library from same repo hides main checkout values

A Jenkinsfile that retrieves a library from the same repository that provided the Jenkinsfile will confuse the commit and branch name in the checkout step.
