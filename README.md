# [JENKINS-45489](https://issues.jenkins-ci.org/browse/JENKINS-15103) open pack file prevents workspace wipe

The return value from checkout(scm) in the pipeline does not consistently
match the repository on which the checkout is performed.  The specific
case mentioned involves use of a library before the checkout whose return
value is being captured.
