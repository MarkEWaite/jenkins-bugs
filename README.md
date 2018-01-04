# [JENKINS-42440](https://issues.jenkins-ci.org/browse/JENKINS-42440) declarative not performing checkout

Boris Folgmann noted that declarative was not performing a checkout
for him with his declarative pipeline job.  Unfortunately, he provided
no further details.  The original bug describes a scripted pipeline,
not a declarative pipeline, and was resolved by adding the required
`checkout scm` step to the scripted pipeline.
