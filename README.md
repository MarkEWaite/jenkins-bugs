# [JENKINS-59051](https://issues.jenkins.io/browse/JENKINS-59051) declarative syntax tools directive wrong for jgit

The declarative syntax generator provides the wrong syntax for jgit and jgitapache.
It provides the correct syntax for command line git and multiple installers that
use command line git.

This bug requires interactive verification by calling the syntax generator.
This branch is used to confirm that the correct declarative syntax works as expected.
Scripting the test would be painful, at best.
