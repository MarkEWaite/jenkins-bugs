# Public repository for Jenkins bug verification.

Many of the bug reports on the Jenkins git plugin and the Jenkins git
client plugin need a repository which contains specific configurations to
duplicate the bug.  This repository captures some of those configurations
in a way that is publicly visible so that automated tests can use this
repository.

Ant doesn't seem to allow a trailing ^M when creating the commit, or
rather ant's invocation of command line git doesn't seem to allow it.
Even command line git is struggling to allow it, though it might allow
it with a multi-line commit where the first line ends in ^M

Multi-line commit with line 1 ending in ^M didn't seem to do it either
