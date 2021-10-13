# [JENKINS-36637](https://issues.jenkins.io/browse/JENKINS-36637) change list incomplete with some characters

This branch shows that certain characters damage the git plugin changelog.  Especially the 'ESC' character.  That is an odd character to include in a commit message, but "it happens".

The job also starts twice for a single commit.  I assume that is due to a
race condition in the polling code (two polls started by the notifyCommit
from the cache repo very quickly).  I redefined the post-receive hook to
only check 1 of the 3 repository URL forms (skipping the ssh authenticated
form and the git protocol form).  The check job uses the https form,
so I expect this will still trigger a second build.

Removing 2 of 3 was the key change.  Now the job runs only once (since
there is only a single notifyCommit).

When the commits are empty, they do not appear in the list of changes with git plugin 4.9.0 and Jenkins 2.303.2.
