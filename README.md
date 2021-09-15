# [JENKINS-25465](https://issues.jenkins.io/browse/JENKINS-25465) GIT_BRANCH does not shorten when branch name contains '/'

The `GIT_BRANCH` token macro documents that it provides the short form of the branch name by default and provides the long form if the `fullName=true` argument is used.

If the branch name includes a '/', then the `GIT_BRANCH` token macro provides an expanded form of the branch name.
If the branch name does not include a '/', then the short form of the branch name is provided.

## Short form

If branch name at checkout is `master`, then `${GIT_BRANCH}` will be `master` and `${GIT_BRANCH,fullName=true}` will be `origin/master`.

## Long form

If branch name at checkout is `*/master`, then `${GIT_BRANCH}` will be `refs/origin/master` and `${GIT_BRANCH,fullName=true}` will be `refs/remotes/origin/master`.
