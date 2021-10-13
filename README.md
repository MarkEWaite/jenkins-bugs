# [JENKINS-36637](https://issues.jenkins.io/browse/JENKINS-36637) change list incomplete with some characters

This branch shows that certain characters damage the git plugin changelog
only from command line git, not from jgit.

The job also starts twice for a single commit.  I assume that is due to a
race condition in the polling code (two polls started by the notifyCommit
from the cache repo very quickly).  I redefined the post-receive hook to
only check 1 of the 3 repository URL forms (skipping the ssh authenticated
form and the git protocol form).  The check job uses the https form,
so I expect this will still trigger a second build.

Removing 2 of 3 was the key change.  Now the job runs only once (since
there is only a single notifyCommit).

More investigation is still required. The bug check pipeline is now
configured to use groovy to read the last few commits. If no change is
reported, then the job is assumed to have failed.

Moved the assertions into Jenkinsfile so they can be coded in groovy.

Not clear why the job missed the second change I submitted.

Tue Oct 12 05:59:12 PM MDT 2021

JGit version of this job is reporting none of the interesting commits.
It only reports commits that I run from the git command line.  I don't
understand how there is any difference between them, yet that is the
behavior I see in multiple jobs.

Tue Oct 12 06:01:25 PM MDT 2021

Tried submitting a standard commit, a batch of interesting commits,
and then the commit that includes this change.
