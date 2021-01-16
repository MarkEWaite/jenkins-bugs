# [JENKINS-23476](https://issues.jenkins.io/browse/JENKINS-23476) default timeout not easily configured

The git plugin global timeout setting is not easily configured.

The docker instance which tests this bug sets the timeout with
a configuration property from the java command line.  With that
configuration, all occurences of the 10 minute default timeout should
be changed to the value of the configuration property.

Unfortunately, they are not.  This job shows that problem.
