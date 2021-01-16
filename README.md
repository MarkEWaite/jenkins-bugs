# [JENKINS-60204](https://issues.jenkins.io/browse/JENKINS-60204) Submodule update fails with 'Needed a single revision'

A project with a git submodule was working as expected with git plugin 3.12 but fails with git plugin 4.0.0.  The failure message is:

```
hudson.plugins.git.GitException: Command "git submodule update --remote impl/tool_protec" returned status code 1:
stdout: 
stderr: fatal: Needed a single revision
Unable to find current origin/development revision in submodule path 'impl/tool_protec'
```

I am unable to duplicate the problem.  Refer to the [Jenkinsfile](Jenkinsfile) in this branch for the settings I used in my attempt.
