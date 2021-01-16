# [JENKINS-56150](https://issues.jenkins.io/browse/JENKINS-56150) NPE during submodule update with empty .gitmodules file

If a git repository has an empty .gitmodules file, the git client plugin will assume that the repository has submodules. When the submodule update is run, it crashes with what a null pointer exception.
