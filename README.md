# [JENKINS-20941](https://issues.jenkins.io/browse/JENKINS-20941) - Submodule authentication

Allow submodules to authenticate with the same credentials used for
the parent repository.

= Bugs to Explore =

* bin-2 and elisp-2 sometimes checkout wrong branch (master)
* Removed bin-3 submodule and job crashed on at least one machine
* After bin-3 submodule removed, polling ignored changes

== Removed bin-3 submodule ==

Stack trace was:

  hudson.plugins.git.GitException: Command "git submodule update --init --recursive --remote --reference /var/lib/git/mwaite/bin.git bin-3" returned status code 1:
  stdout: 
  stderr: error: pathspec 'bin-3' did not match any file(s) known to git.
  Did you forget to 'git add'?

	at org.jenkinsci.plugins.gitclient.CliGitAPIImpl.launchCommandIn(CliGitAPIImpl.java:1752)
	at org.jenkinsci.plugins.gitclient.CliGitAPIImpl.launchCommandWithCredentials(CliGitAPIImpl.java:1495)
	at org.jenkinsci.plugins.gitclient.CliGitAPIImpl.access$300(CliGitAPIImpl.java:64)
	at org.jenkinsci.plugins.gitclient.CliGitAPIImpl$7.execute(CliGitAPIImpl.java:1038)
	at org.jenkinsci.plugins.gitclient.RemoteGitImpl$CommandInvocationHandler$1.call(RemoteGitImpl.java:152)
	at org.jenkinsci.plugins.gitclient.RemoteGitImpl$CommandInvocationHandler$1.call(RemoteGitImpl.java:145)
	at hudson.remoting.UserRequest.perform(UserRequest.java:153)
	
