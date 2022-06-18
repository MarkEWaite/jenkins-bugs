# [JENKINS-68751](https://issues.jenkins.io/browse/JENKINS-68751) Non-global ssh key credential unusable with Pipeline

User reports that an ssh credential cannot be used with a Pipeline checkout if it is not globally scoped.

I can't duplicate the problem as described.  Steps that I took while trying to duplicate the problem:

1. Create a new credential domain "gitea-server.markwaite.net" under the "System" domain
2. Configure the new credential domain to include "gitea-server.markwaite.net" and only support URI scheme "ssh"
3. Add a new ED 25519 private key named "JENKINS-68751 exploratory ED 25519 private key"
4. Create a new Pipeline job named "JENKINS-68751-non-global-ssh-key-credential-unavailable-to-pipeline"
5. Define the Pipeline for that job to be read from git@gitea-server.markwaite.net:mwaite/bin.git using the credential "JENKINS-68751 exploratory ED 25519 private key"
6. Confirm that the credential works as expected by running the Pipeline

I've stored the results of those steps in my docker image so that I can refer to them in the future, but it shows me that the code is working as expected. Any suggestions what I'm doing differently compared to what you're doing? I'm using the latest plugin versions as described in my docker-lfs repository.

I also confirmed that the credential I had defined was not visible when I attempted to use a repository with a different URL, like git@github.com:MarkEWaite/bin.git
