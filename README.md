# [JENKINS-43818](https://issues.jenkins.io/browse/JENKINS-43818) Branch parameter ignored

In a pipeline job from a git repository where the "Branch Specifier"
is given as a parameter, I started getting the following failure after
updating the git plugin.

```
hudson.plugins.git.GitException: Command "git fetch --tags --progress origin +refs/heads/$\{GITREF}:refs/remotes/origin/$\{GITREF} --prune" returned status code 128:
stdout: 
stderr: fatal: Couldn't find remote ref refs/heads/$\{GITREF}

        at org.jenkinsci.plugins.gitclient.CliGitAPIImpl.launchCommandIn(CliGitAPIImpl.java:1799)
        at org.jenkinsci.plugins.gitclient.CliGitAPIImpl.launchCommandWithCredentials(CliGitAPIImpl.java:1525)
        at org.jenkinsci.plugins.gitclient.CliGitAPIImpl.access$300(CliGitAPIImpl.java:65)
        at org.jenkinsci.plugins.gitclient.CliGitAPIImpl$1.execute(CliGitAPIImpl.java:316)
        at jenkins.plugins.git.GitSCMFileSystem$BuilderImpl.build(GitSCMFileSystem.java:304)
        at jenkins.scm.api.SCMFileSystem.of(SCMFileSystem.java:196)
        at jenkins.scm.api.SCMFileSystem.of(SCMFileSystem.java:172)
        at org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition.create(CpsScmFlowDefinition.java:99)
        at org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition.create(CpsScmFlowDefinition.java:59)
        at org.jenkinsci.plugins.workflow.job.WorkflowRun.run(WorkflowRun.java:232)
        at hudson.model.ResourceController.execute(ResourceController.java:98)
        at hudson.model.Executor.run(Executor.java:404)
Finished: FAILURE
```

The user tested all released versions of the git plugin from 3.0.1 to
3.2.0 and the failure seems to have appeared in 3.0.2.
