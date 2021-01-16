# [JENKINS-46054](https://issues.jenkins.io/browse/JENKINS-46054) - submodule with '.url' in repo URL won't clone

If a submodule URL includes the string '.url', git client plugin fails
to clone the submodule.  Regular expression matching the output of the
git config command was too greedy.
