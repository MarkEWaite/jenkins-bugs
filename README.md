# [JENKINS-43468](https://issues.jenkins.io/browse/JENKINS-43468) False detection of changes with simple 'checkout scm'

This Jenkins job polls every 29 minutes.  Intentionally doesn't define
a notifyCommit repository so the notification will not be called for
commits.  GitHub webhooks are not currently configured to reach inside
my private network, so changes on this branch should only be detected
by the polling defined in the Jenkinsfile.

Bug found that running this pipeline job with the global pipeline library
on which it depends and the simple 'checkout scm' will cause a build
each polling cycle, whether there were changes or not.

The more complex checkout command does not show the same problem.

I believe this is a duplicate of one or more other bugs, but could not
find the bug report.

Refer to use_simple_checkout_scm in Jenkinsfile for the toggle.

As of Sat 15 Aug 2020, I cannot duplicate the problem in the cases
I've tested.
