# [JENKINS-45489](https://issues.jenkins.io/browse/JENKINS-45489) checkout step returns some wrong variables if library is used

The return value from checkout(scm) in the pipeline does not consistently
match the repository on which the checkout is performed.
