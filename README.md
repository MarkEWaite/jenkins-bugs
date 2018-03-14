# [JENKINS-50158](https://issues.jenkins-ci.org/browse/JENKINS-50158) checkout from library reported LocalBranch not serializable

An interesting syntactic twist on the checkout command was used in
[JENKINS-50158](https://issues.jenkins-ci.org/browse/JENKINS-50158) :

```
def gitscm = new GitScm(... , extensions: [new LocalBranch('**')])
def scmvars = checkout gitscm
```

That syntax worked in a Jenkinsfile but did not work when reference in
a Pipeline shared library.  The Jenkinsfile in this repository shows a
technique that works from a Pipeline shared library.
