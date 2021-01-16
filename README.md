# [JENKINS-6203](https://issues.jenkins.io/browse/JENKINS-6203) - UTF-8 not used to read changelog

The git changelog was previously processed with the default runtime
character set, even though git writes it (by default) as UTF-8.

Other issues explored by this job include:

* Pipeline warning "Using the ‘stage’ step without a block argument is deprecated"
* Private pipeline job fails to detect changes, while public detects
