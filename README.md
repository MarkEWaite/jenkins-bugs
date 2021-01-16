# [JENKINS-34309](https://issues.jenkins.io/browse/JENKINS-34309) Exception if message includes Ctrl-M

A commit message formatted on some platforms may contain an embedded
Ctrl-M character.  That embedded Ctrl-M character was causing an exception
in parsing the commit ID.

Ant doesn't seem to allow a trailing ^M when creating the commit, or
rather ant's invocation of command line git doesn't seem to allow it.
Even command line git is struggling to allow it, though it might allow
it with a multi-line commit where the first line ends in ^M

Multi-line commit with line 1 ending in ^M didn't seem to do it either.
I had to embed the Ctrl-M before another character.
