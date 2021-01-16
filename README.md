# [JENKINS-26100](https://issues.jenkins.io/browse/JENKINS-26100) - skip default checkout and use git env vars

Declarative pipeline includes the option to skip the default checkout.
When default checkout is skipped, the pipeline may still want to refer to git related environment variables.
For example, `GIT_COMMIT` or `GIT_BRANCH` may be important variables to use elsewhere in the declarative pipeline.
