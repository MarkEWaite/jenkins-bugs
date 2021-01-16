# [JENKINS-28529](https://issues.jenkins.io/browse/JENKINS-28529) polling cleans workspace unexpectedly

If "clean before checkout" is enabled and any of the "ignore" settings
are enabled (ignore commits in specific paths, ignore commits from
specific users, ignore commits with specific messages), then polling
will clean the workspace.  Polling should not clean the workspace.
That's not the responsibility of polling.

If "clean after checkout" is used instead, the workspace is not cleaned
by polling.
