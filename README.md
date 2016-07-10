# Public repository for Jenkins bug verification.

Many of the bug reports on the Jenkins git plugin and the Jenkins git
client plugin need a repository which contains specific configurations to
duplicate the bug.  This repository captures some of those configurations
in a way that is publicly visible so that automated tests can use this
repository.

This repository will eventually be extended to have a Jenkinsfile pipeline
definition file for those branches where the pipeline definition file
can encapsulate the bug verification step.
