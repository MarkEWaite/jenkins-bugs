# [JENKINS-62534](https://issues.jenkins.io/browse/JENKINS-62534) job with plink in name fails to clone

 Any job containing the word "plink" fails to clone with command line git 1.7 (CentOS 6) and command line git 1.8 (CentOS 7).
 Since we are using a multi-branch pipeline, this causes any branch with the word "plink" to mysteriously fail.

The logs show this strange error message.

```
stderr: getaddrinfo: atch: Name or service not known
```

Then it tries to use the port from the URL as if were a hostname.

```
ssh: connect to host 7999 port 22: Success
```

I don't know how that connection could have succeeded.
Then we get another error.

```
fatal: Could not read from remote repository.
```

Alternatives include:

* Use JGit instead of those very old command line git versions
* Install a newer version of command line git
* Clone with http or https protocol instead of ssh protocol
