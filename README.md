# [JENKINS-36451](https://issues.jenkins.io/browse/JENKINS-36451) repo browser URL not saved in Pipeline project config

A parameterized Pipeline job (not multi-branch?) using Pipeline script
from SCM with a value for gitiles repository browser will lose the
repository browser setting when the form is saved.
