# [JENKINS-50886](https://issues.jenkins.io/browse/JENKINS-50886) Pipeline starts new job for same SHA1 if build runs longer than polling interval

The bug reports that Pipeline polling will start a new job with the same
SHA1 that is already building if the polling interval is less than the
build duration.

Polling is resource intensive and generally not recommended.  Refer to
the "polling must die" blog post from Kohsuke Kawaguchi for a good
explanation.

Note that this repository must not have the cache or bare remotes
available.  It depends on the commit being written only to the github.com
repository so that notifyCommit does not inform the Jenkins server before
the next polling interval.
