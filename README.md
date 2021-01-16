# [JENKINS-xxxxx](https://issues.jenkins.io/browse/JENKINS-xxxxx) check public repository

Many of the bug reports on the Jenkins git plugin and the Jenkins git
client plugin need a repository which contains specific configurations
to duplicate the bug.  This repository publicly captures configurations
so that automated tests can use this repository.

This repository includes many branches with a Jenkinsfile pipeline
definition where the pipeline definition can encapsulate a portion of the
bug verification. It is an imperfect attempt to accelerate interactive
testing of Jenkins bug reports while allowing some reuse of the work to
test those bug reports.

The master branch is used in several freestyle jobs and is branched to JENKINS-61120.
Pull requests may be submitted against the master branch from JENKINS-61120.
