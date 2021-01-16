# [JENKINS-64000](https://issues.jenkins.io/browse/JENKINS-64000) Git tags setting is ignored by initial repository fetch

The tags setting was being ignored in the pipeline definition so that tags were not available in the cloned workspace.
