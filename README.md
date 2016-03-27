# [JENKINS-33827](https://issues.jenkins-ci.org/browse/JENKINS-33827) Bug Verification

Multiple build chooser strategy specifications within the same job definition seem to interact
in unexpected ways.  This page includes a table that defines branches which are expected to 
be built with various branch choosing strategies and the defined branch specs.

It is hoped that someday this might be used to automatically create jobs which test these
conditions are satisfied.  Until then, this is a reference for interactive testing.

## BranchSpec: \*/master, \*/JENKINS-1\*, \*/JENKINS-\*\*2, \*/JENKINS-\*3

| Branch                   | Default | Inverse | Ancestry | Default + Inverse | Inverse + Ancestry | Inverse + Inverse |
|--------------------------|---------|---------|----------|-------------------|--------------------|-------------------|
|    JENKINS-11337/develop |         |    X    |          |         O         |                    |                   |
|     JENKINS-11337/master |         |    O    |          |         O         |                    |                   |
|            JENKINS-14798 |    X    |         |          |         Y         |                    |         X         |
|            JENKINS-33202 |    X    |         |          |         Y         |                    |         X         |
| JENKINS-33202-x/branch-1 |         |    X    |          |         O         |                    |                   |
| JENKINS-33202-x/branch-2 |    X    |         |          |         Y         |                    |         X         |
|   JENKINS-33433-branch-1 |         |    X    |          |         O         |          X         |                   |
|   JENKINS-33433-branch-2 |    X    |         |          |         Y         |          X         |         X         |
|     JENKINS-33433-master |         |    X    |          |         O         |          X         |                   |
|            JENKINS-33695 |         |    O    |          |         O         |                    |                   |
|            JENKINS-33827 |         |    X    |          |         O         |                    |                   |
|             JENKINS-6203 |    X    |         |          |         Y         |                    |         X         |
|                   master |    O    |         |          |                   |                    |         X         |

In columns where there are mixes of "X" and "O", the "O" means that I predicted that branch
would be built, but it was not built in the git plugin 2.4.4 test that I ran with Jenkins 2.0 beta.
In columns where there are mixes of "X" and "Y", the "Y" means that the branch was built, even
though I predicted it would not be built.

In the "Default + Inverse" column, it appears that the column exactly matches the "Default" column,
as though the additional "Inverse" build chooser was completely ignored.
