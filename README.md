# [JENKINS-58349](https://issues.jenkins.io/browse/JENKINS-58349) Timeout ignored for pipeline checkout

Bug report says that the Pipeline checkout timeout setting is ignored.
This repository includes a Jenkinsfile that reads from the Linux kernel
git repository (larger than 2 GB repository) with an intentionally
short timeout.  The checkout step honors the timeout setting.

The issue is likely when a large repository includes a Jenkinsfile.
The initial checkout to obtain the Jenkinsfile does not honor the timeout.
That failure was reported as [JENKINS-38973](https://issues.jenkins.io/browse/JENKINS-38973).
