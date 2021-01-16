# [JENKINS-56326](https://issues.jenkins.io/browse/JENKINS-56326) Later checkout fails if new commit to master branch

Bug is described as:

1. Commit to master branch
2. Commit to non-master branch
3. Start long running build on non-master branch
4. During long running build on non-master branch, commit to master branch
5. During long running build on non-master branch, perform a new checkout

Expected result: New checkout inside the build should use the same SHA1 as 
the original checkout that started the build.

Reported result: Command line git error performing a fetch, cannot lock ref 'refs/remotes/origin/master'

I was unable to duplicate the bug.
