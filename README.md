# [JENKINS-38860](https://issues.jenkins.io/browse/JENKINS-38860) submodule config changes not reflected in workspace

When a submodule is deleted from a repository and that delete is
committed, that deletion is not reflected in the Jenkins workspace.
Remnants are left in the workspace which make the workspace no longer
an accurate representation of the repository that would result from a
fresh checkout.

The temporary steps to avoid the problem may include:
* wipe the workspace each time the job runs
* deleteDir the workspace (if using pipeline)
