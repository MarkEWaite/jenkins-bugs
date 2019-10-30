# Contributing

Please follow these contribution guidelines:

* Each bug is represented as one or more branches, commonly named by the bug ID being checked
* Each branch should include a Jenkinsfile that performs verification steps
* Each branch should include a [README.md](README.md) that briefly describes the bug and links to the original bug report
* Build tools are preferred to be platform portable (ant or maven)
* Jobs are preferred to run on any available platform (windows, linux, freebsd, macos, openbsd, etc.).
  If they must be limited to specific platforms, use a platform label.
  If they require specific tools, use a tool label.
* Platform labels are maintained by the platformlabeler plugin and include:
    * __windows__ - Microsoft Windows
    * __linux__   - a Linux variant (no commitment which variant, could be alpine, CentOS, Debian, Ubuntu, or several others)
    * __cloud__   - indicate no agent access to Jenkins server root URL
* Tool labels are manually maintained and include
    * __ant-latest__ - latest release of Apache Ant
    * __git-X.YY+__  - minimum required CLI git version for the test (for example, git-1.8+ means "CLI git 1.8 or newer")

Verification is most commonly run from a derivative of the [lts-with-plugins branch of docker-lfs](https://github.com/MarkEWaite/docker-lfs/tree/lts-with-plugins).

Contents of this branch are checked by [pre-commit](https://pre-commit.com/) for simple file consistency checks when the pre-commit program is installed on a development machine.
