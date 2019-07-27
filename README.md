# [JENKINS-33202](https://issues.jenkins-ci.org/browse/JENKINS-33202) - LocalBranch should allow '**' to match origin branch

Maven SCM support for branches relies on the local branch name being the
same as the remote branch name. Tools such as the maven-release-plugin
will push changes using the same local and remote branch names as
explained at https://maven.apache.org/scm/git.html

Ex. git push pushUrl currentBranch:currentBranch

Jenkins jobs that perform a maven release MUST configure the LocalBranch
extension with the correct branch name sans the remote name. For example,
if building a release for origin/master, you must configure LocalBranch
to be master.

To facilitate this requirement, LocalBranch values of "**" or null should
be allowed and result in using the remote branch name.
