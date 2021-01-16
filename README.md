# [JENKINS-55536](https://issues.jenkins.io/browse/JENKINS-55536) Use multiple remotes in Jenkinsfile

The bug report says that multiple remotes are not supported by the git
plugin.  As far as I can tell, multiple remotes are supported in all the
job types that I've used, including Freestyle, Matrix, Scripted Pipeline,
and Declarative Pipeline jobs.

Multiple remotes are not available in the branch source plugins that use
REST API calls to improve change detection performance.  A GitHub branch
source, Bitbucket branch source, or Gitea based multibranch Pipeline
will need to declare the additional remotes in the Jenkinsfile rather
than declaring it in the configuration of the multibranch Pipeline.
