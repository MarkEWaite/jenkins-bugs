# [JENKINS-63653](https://issues.jenkins.io/browse/JENKINS-63653) - Check scm.branches has expected value

The report says that scm.branches is assigned the name of the pull request.
That is confirmed with git plugin 4.4.1 and expected with others.
User reports that the value of scm.branches makes it so that the checkout fails because it cannot find a revision to checkout.
User reports that if an extension clause is added to the checkout, it succeeds.

Assumed that the user is running the job from the GitHub branch source, but that needs to be confirmed.
