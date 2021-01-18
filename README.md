# [JENKINS-64656](https://issues.jenkins.io/browse/JENKINS-64656) Message with regular expression not ignored by polling

Intentionally did not define the cache repository so that no notifyCommit will be called for commits to this branch.
GitHub webhooks are not currently configured to reach inside my private network, so changes on this branch should only be detected by the polling defined in the Jenkinsfile.

Polling by a Freestyle job should ignore the commit messages created by build.xml.
Polling by a Pipeline job is not expected to honor the git plugin setting to ignore messages based on a specific string.
Pipeline jobs that need to ignore specific messages should do that from a higher level Pipeline definition, not from a git plugin setting.
