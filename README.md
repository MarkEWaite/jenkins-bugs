[JENKINS-33433](https://issues.jenkins-ci.org/browse/JENKINS-33433) - NPE on merge from master to feature branch

A null pointer exception is reported in the google-source-plugin when merging out from
the master branch to the feature branch. I don't think there is anything which the
git plugin can do to resolve that, but this branch allows me some experiments.
