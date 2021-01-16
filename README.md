# [JENKINS-58587](https://issues.jenkins.io/browse/JENKINS-58587) Second commit can't compute SHA1

Bug is described as:

1. Checkout to a Windows workspace, compute the SHA1 of HEAD
2. Checkout to a different Windows workspace, compute the SHA1 of HEAD

Expected result: Second checkout computes SHA1 as readily as first checkout

I was unable to duplicate the bug.
