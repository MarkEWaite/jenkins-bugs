# [JENKINS-48938](https://issues.jenkins.io/browse/JENKINS-48938) - Pipeline job builds every polling interval

The BuildData tracked by the git plugin does not include the SCM name ("origin", etc.) in the recorded data.
Polling checks the BuildData from the preceding build to decide if a new build is needed.
The check seems to short-circuit after checking the first repository, even though the specific ID is the second.
Likely needs significant time in the BuildData to understand the behavior and the rationale.

The bug does **not** duplicate with this simple configuration.
More investigation will be needed based on the excellent details described in the bug report.
