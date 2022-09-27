# [JENKINS-69081](https://issues.jenkins.io/browse/JENKINS-69081) http_request now tracks credential use

The http_request plugin previously did not provide credential use tracking.
[PR-113](https://github.com/jenkinsci/http-request-plugin/pull/113) adds credential use tracking so that jobs using an http_request credential are identified.
