# [JENKINS-42860](https://issues.jenkins.io/browse/JENKINS-42860) getBranches method should be whitelisted for Pipeline use

Users would like to access the GitSCM getBranches method to read the
branches in the current workspace.  Andrew Bayer indicates that is
allowed if the method is annotated with "@Whitelisted".
