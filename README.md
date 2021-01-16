[JENKINS-33433](https://issues.jenkins.io/browse/JENKINS-33433) - NPE on merge from master to feature branch

A null pointer exception is reported in the google-source-plugin when
merging out from the master branch to the feature branch. I don't think
there is anything which the git plugin can do to resolve that, but this
branch allows me some experiments.

The git changelog was previously processed with the default runtime
character set, even though git writes it (by default) as UTF-8.

A commit after a commit on the feature branch should show the problem.
Problem is, I don't have the google-source-plugin installed.
Other problem is, I'm not sure it would show the problem even with it.
