# [JENKINS-50401](https://issues.jenkins-ci.org/browse/JENKINS-50401) local branch extension causes incorrect checkout

If the git plugin uses a local branch which is named the same as the
remote and contains a forward slash, it will cause the checkout command
to not update to the latest commit.

See also [JENKINS-37263](https://issues.jenkins-ci.org/browse/JENKINS-37263)
