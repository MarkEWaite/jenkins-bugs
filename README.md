# [JENKINS-18834](https://issues.jenkins.io/browse/JENKINS-18834) - initial git fetch does not prune

When prune is requested, it should be performed by every call to git
fetch, not just by the second call to git fetch.
