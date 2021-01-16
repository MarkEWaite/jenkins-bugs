# [JENKINS-40529](https://issues.jenkins.io/browse/JENKINS-40529) - prune stale tags

Tags in the local workspace which do not exist in the remote repository may be removed
during fetch by the "prune stale tags" extension.  Test that extension.

Stale tags are only visible when a workspace has been reused.  A freshly
cloned workspace will not have stale tags because the remove script
removes all remote tags except the tag it will immediately push to
the remote repostory.  Because stale tags are only visible in reused
workspaces, the counting script includes logic that detects if a
workspace has been reused.  If this is the first use of the workspace,
then no stale tag count is reported.
