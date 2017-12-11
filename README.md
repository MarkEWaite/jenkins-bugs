# [JENKINS-47496](https://issues.jenkins-ci.org/browse/JENKINS-47496) new tags discovered but not built

Tag discovery was added to the git plugin SCM API in git plugin 3.6.0.
The bug report was that the tags are discovered but not built.

This job is also used to check [JENKINS-14917](https://issues.jenkins-ci.org/browse/JENKINS-14917) - Build is not triggered for new tag (without new commit).
