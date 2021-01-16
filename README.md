# [JENKINS-63179](https://issues.jenkins.io/browse/JENKINS-60591) Changing branch to build does not detect changes on updated branch

Bug is described as:

Create a pipeline job definition 'Pipeline script from SCM'

Fill settings: SCM: GIT, repo url and credentials
   Branch Specifier: * leave blank
   Script path: path to jenkinsfile
   Lightweight checkout: disabled

In 'Build triggers' section enable 'Poll SCM' and set schedule: 'H/2 * * * *'

Let's imagine your repo has two branches: master and JENKINS-63179

After initial settings everything works fine: commits to any of branches trigger a new build.

Now next step:

change Branch Specifier to refs/heads/master and commit to both branches.
Build is triggered only on commit to 'master' branch - that's OK.

Now:

change Branch Specifier back to blank value (or refs/heads/* or *)
Commit to both branches and see how build is triggered only on master branch.

Expected behavior was to trigger builds for both branches.

Expected result: Checkout should use first SHA-1, not second SHA-1 

Reported result: Checkout used second SHA-1
