# [JENKINS-64656](https://issues.jenkins.io/browse/JENKINS-64656) Message with regular expression not ignored by polling

Intentionally did not define the cache repository so that no notifyCommit will be called for commits to this branch.
GitHub webhooks are not currently configured to reach inside my private network, so changes on this branch should only be detected by the polling defined in the Jenkinsfile.

Polling by a Freestyle job with the polling message exclusion should ignore the commit messages created by build.xml.

Polling by a Pipeline job is not expected to honor the git plugin setting to ignore messages based on a specific string.
Pipeline jobs that need to ignore specific messages should use a higher level Pipeline definition, not a setting in the git plugin.

The exclusion is not honored when the string is:

  .*\[maven-release-plugin\].*

The exclusion is honored when the string is:

  (?s).*\[maven-release-plugin\].*

Sadly, the failing example is the example listed in the online help.
The failing example should work, but doesn't.
