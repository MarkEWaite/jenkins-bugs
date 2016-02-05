# [JENKINS-11337](https://issues.jenkins-ci.org/browse/JENKINS-11337) Multiple branches ignored

A [git-flow branching workflow](http://nvie.com/posts/a-successful-git-branching-model/)
was not detecting changes when Jochen Ulrich defined "Branches to Build" as
"**" and added the additional behavior to prune stale remote-tracking
branches.

Steps I took trying to duplicate the problem:

* Install git-flow on my Ubuntu development machine
* Initialize the git-flow branching: `git checkout -b JENKINS-11337/master`
* Create a develop branch: `git checkout -b JENKINS-11337/develop`
* Initialize git flow branching: `git flow init`
* Start a feature branch named bug-check-1: `git flow feature start bug-check-1`
