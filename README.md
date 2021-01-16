# [JENKINS-59785](https://issues.jenkins.io/browse/JENKINS-59785) GitChangeSetList not serializable

Jenkins Declarative Pipelines that reference changesets are sometimes reporting an
exception that the changeset can't be serialized.
