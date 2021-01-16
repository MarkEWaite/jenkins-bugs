# [JENKINS-31826](https://issues.jenkins.io/browse/JENKINS-31826) - Checkout specific SHA1

User reported that they were unable to checkout a specific SHA1
with Pipeline.  I was unable to duplicate the bug with Freestyle.
Another user said they had the same problem.  This is my confirmation
test that arbitrary non-tip SHA1 hashes can be used for Pipeline checkout.
