# [JENKINS-57587](https://issues.jenkins.io/browse/JENKINS-57587) NPE on lightweight checkout

With the most basic Pipeline job from SCM with "Lightweight checkout" checked, the following NPE trace is the total job output.

Unchecking "Lightweight checkout" solves the immediate NPE issue and the job completes.

```
Started by user <redacted>java.lang.NullPointerException
 at jenkins.plugins.git.GitSCMFileSystem$1.invoke(GitSCMFileSystem.java:117)
 at jenkins.plugins.git.GitSCMFileSystem$1.invoke(GitSCMFileSystem.java:114)
 at jenkins.plugins.git.GitSCMFileSystem$3.invoke(GitSCMFileSystem.java:193)
 at org.jenkinsci.plugins.gitclient.AbstractGitAPIImpl.withRepository(AbstractGitAPIImpl.java:29)
 at org.jenkinsci.plugins.gitclient.CliGitAPIImpl.withRepository(CliGitAPIImpl.java:72)
 at jenkins.plugins.git.GitSCMFileSystem.invoke(GitSCMFileSystem.java:189)
 at jenkins.plugins.git.GitSCMFileSystem.<init>(GitSCMFileSystem.java:114)
 at jenkins.plugins.git.GitSCMFileSystem$BuilderImpl.build(GitSCMFileSystem.java:353)
 at jenkins.scm.api.SCMFileSystem.of(SCMFileSystem.java:198)
 at jenkins.scm.api.SCMFileSystem.of(SCMFileSystem.java:174)
 at org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition.create(CpsScmFlowDefinition.java:108)
 at org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition.create(CpsScmFlowDefinition.java:67)
 at org.jenkinsci.plugins.workflow.job.WorkflowRun.run(WorkflowRun.java:293)
 at hudson.model.ResourceController.execute(ResourceController.java:97)
 at hudson.model.Executor.run(Executor.java:429)
Finished: FAILURE
```

I was unable to duplicate the problem as described in the initial bug report.
