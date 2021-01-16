# [JENKINS-51638](https://issues.jenkins.io/browse/JENKINS-51638) - Jenkinsfile merge parameter string disallowed

The string 'default' was accepted as a merge parameter in git plugin 3.8.0 and prior.  It was not accepted as a value beginning with git plugin 3.9.x releases.

To duplicate the bug, try the following steps:

1. Create a base branch named '[JENKINS-51638](https://github.com/MarkEWaite/jenkins-bugs/tree/JENKINS-51638)' with subdirectory project-1
2. Create a Pipeline job monitoring 'JENKINS-51638'
3. Create a project branch 'JENKINS-51638-project-1' which will only make changes in the project-1 subdirectory
4. Create a Pipeline job monitoring 'JENKINS-51638-project-1'
5. Commit to the project-1 branch and confirm that only the project-1 branch job runs
6. Commit to the base branch and confirm that the project-1 changes are merged
