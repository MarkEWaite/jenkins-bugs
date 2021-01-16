# [JENKINS-15103](https://issues.jenkins.io/browse/JENKINS-15103) open pack file prevents workspace wipe

A pack file was left open with command line git and with JGit if the
branch specifier in a freestyle job used a wildcard character at the
end of the branch specifier.
