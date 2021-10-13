# [JENKINS-66885](https://issues.jenkins.io/browse/JENKINS-66885) change list incomplete with some characters

This branch shows that empty commits are not shown as changes in JGit multibranch Pipeline jobs.

If the commit changes a file, it will appear in the changes list.
If tthe commit does not change a file, it will not appear in the list.
