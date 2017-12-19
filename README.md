# [JENKINS-48589](https://issues.jenkins-ci.org/browse/JENKINS-48589) - empty committer e-mail causes changeset exception

An empty committer e-mail causes a changeset exception in some cases.

This repository depends on an edit .git/config file which declares
the email to be an empty value.  Even with that, I still don't see the
failure.  The stake trace from the original report is using a different
credential system.  I assume it is invalid to have an empty user name in
any system, so I believe the change is safe to treat an empty username
or empty email address as unknown user.
