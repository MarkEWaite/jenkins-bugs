# [JENKINS-62534](https://issues.jenkins-ci.org/browse/JENKINS-62534) job with plink in name fails to clone

 Any job containing the word "plink" fails to clone.
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
