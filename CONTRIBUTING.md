# Contributing

Please follow these contribution guidelines:

* Each bug is represented as one or more branches, commonly named by the bug ID being checked
* Each branch should include a Jenkinsfile that performs verification steps
* Each branch should include a [README.md](README.md) that briefly describes the bug and links to the original bug report
* Build tools are preferred to be platform portable (ant or maven)
* Jobs are preferred to run on any available platform (windows, linux, freebsd, etc.). 
  If they must be limited to specific platforms, use a node label. 
  Platform labels currently include windows, linux, and cloud (to indicate no agent access to Jenkins server root URL). 
  Tool labels currently used include git-X.YY+ to indicate the minimum required CLI git version for the test

Verification is most commonly run from the [lts-with-plugins branch of docker-lfs](https://github.com/MarkEWaite/docker-lfs/tree/lts-with-plugins).
Verification jobs may assume the existence of labels `linux`, `windows`, `maven-latest`, `ant-latest`, `git-lfs`
