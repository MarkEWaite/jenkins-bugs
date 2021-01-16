# [JENKINS-43687](https://issues.jenkins.io/browse/JENKINS-43687) Multibranch Pipeline Branch Configuration SCM poll: no changes

Defines this Jenkins job should poll every 5 minutes.  Intentionally did
not define the cache repository so that no notifyCommit will be called
for commits to this branch.  GitHub webhooks are not currently configured
to reach inside my private network, so changes on this branch should
only be detected by the polling defined in the Jenkinsfile.

Unfortunately, this branch does not show JENKINS-43687. It shows a very
different problem, the job builds on each poll, whether by interval or by
"poll now".
