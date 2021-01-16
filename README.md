# [JENKINS-52511](https://issues.jenkins.io/browse/JENKINS-52511) declarative not setting GIT_AUTHOR_NAME

Imran Kahn reported that GIT_AUTHOR_NAME and GIT_COMMITTER_NAME are not
set by declarative Pipeline during its implict checkout.
