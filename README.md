# [JENKINS-14798](https://issues.jenkins.io/browse/JENKINS-14798) - UTF-8 not used to read changelog

The git changelog was previously processed with the default runtime
character set, even though git writes it (by default) as UTF-8.
