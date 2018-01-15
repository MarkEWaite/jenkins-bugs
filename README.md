# [JENKINS-48938](https://issues.jenkins-ci.org/browse/JENKINS-48938) - Pipeline job builds every polling interval

The BuildData tracked by the git plugin does not include the SCM name ("origin", etc.) in the recorded data.
Polling checks the BuildData from the preceding build to decide if a new build is needed.
