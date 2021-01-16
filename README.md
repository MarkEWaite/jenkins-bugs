# [JENKINS-62579](https://issues.jenkins.io/browse/JENKINS-62579) cyrillic named job fails windows clone

When starting the freestyle job on the Windows agent it is not possible to clone the git repository (doesn't matter ssh or https) if the folder name in Jenkins is written in Cyrillic.

```
ERROR: Error cloning remote repo 'origin'
hudson.plugins.git.GitException: Command "git fetch --tags --progress – https://repo_url/repo.git +refs/heads/:refs/remotes/origin/" returned status code 128:
stdout:
stderr: The system cannot find the path specified.
The system cannot find the path specified.
error: unable to read askpass response from 'D:\jenkins_slave\workspace\Тест\windows_https_clone@tmp\jenkins-gitclient-pass0123456789123456789.bat'
fatal: could not read Password for 'https://repo_url/repo.git': terminal prompts disabled
```
 
With English folder names everything goes fine.
 
On Linux agents, there are no problems with cloning repositories using Cyrillic names
 
Java arguments on windows and linux agents are same: 'Dfile.encoding = "UTF-8" -Dsun.jnu.encoding = "UTF-8"'
