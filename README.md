# [JENKINS-33827](https://issues.jenkins-ci.org/browse/JENKINS-33827) Build chooser strategy combinations

Multiple build chooser strategy specifications within the same job
definition seem to interact in unexpected ways.  This page includes a
table that defines branches which are expected to be built with
various branch choosing strategies and the defined branch specs.

It is hoped that someday this might be used to automatically create
jobs which test these conditions are satisfied.  Until then, this is an
interactive test reference.

## BranchSpec: \*/master, \*/JENKINS-1\*, \*/JENKINS-\*\*2, \*/JENKINS-\*3

| Branch                   | D | I | A | D + I | I + A | I + I |
|--------------------------|---|---|---|-------|-------|-------|
|    JENKINS-11337/develop |   | X |   |   O   |   Y   |   Y   |
|     JENKINS-11337/master |   | O |   |   O   |       |       |
|            JENKINS-14798 | X |   |   |   Y   |       |   O   |
|            JENKINS-33202 | X |   |   |   Y   |       |   O   |
| JENKINS-33202-x/branch-1 |   | X |   |   O   |   Y   |   Y   |
| JENKINS-33202-x/branch-2 | X |   |   |   Y   |       |   O   |
|   JENKINS-33433-branch-1 |   | X |   |   O   |   O   |   Y   |
|   JENKINS-33433-branch-2 | X |   |   |   Y   |   X   |   O   |
|     JENKINS-33433-master |   | X |   |   O   |   X   |   Y   |
|            JENKINS-33695 |   | O |   |   O   |       |       |
|            JENKINS-33827 |   | X |   |   O   |   Y   |   Y   |
|             JENKINS-6203 | X |   |   |   Y   |       |   O   |
|                   master | O |   |   |       |       |   O   |

"D" is "Default", "I" is "Inverse", and "A" is "Ancestry with 554eec0303829d1b2774a636fcd9623bf6f3aab0".

In columns where there are mixes of "X" and "O", the "O" means that I
predicted that branch would be built, but it was not built in the git
plugin 2.4.4 test that I ran with Jenkins 2.0 beta.  In columns where
there are mixes of "X" and "Y", the "Y" means that the branch was
built, even though I predicted it would not be built.

In the "Default + Inverse" column, it appears that the column exactly
matches the "Default" column, as though the additional "Inverse" build
chooser was completely ignored.

The "Inverse + Ancestry" column is close to the "Inverse" column, but
does not match it exactly as the "Default + Inverse" exactly machines
the "Default" column.  I don't understand why JENKINS-33433-branch-1
was built in "Default" but was not built in "Inverse + Ancestry".

The "Inverse + Inverse" matches exactly with "Inverse" rather than
matching with "Default" which I expected (inverting an inversion seems
like it should return to the default state).
