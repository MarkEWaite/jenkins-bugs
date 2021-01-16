# [JENKINS-37050](https://issues.jenkins.io/browse/JENKINS-37050) cannot checkout git tag

The `git` task accepts a `branch` argument.  The user wanted to use the
`branch` argument to checkout a specific tag.

Allan B reported that the `git` command simplification prevents that,
while the full syntax of the `checkout` step works.
