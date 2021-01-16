# [JENKINS-52844](https://issues.jenkins.io/browse/JENKINS-52844) - Declarative reports no such DSL method

One user reported on the mailing list that the problem only appeared
if the Jenkinsfile was "UTF-8".  The Jenkinsfile in this repository
includes Japanese characters which are used successfully in other tests.
They are definitely not ASCII characters. I believe they are UTF-8.
