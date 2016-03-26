# [JENKINS-33827](https://issues.jenkins-ci.org/browse/JENKINS-33827) Bug Verification

Multiple build chooser strategy specifications within the same job definition seem to interact
in unexpected ways.  This page includes a table that defines branches which are expected to 
be built with various branch choosing strategies and the defined branch specs.

It is hoped that someday this might be used to automatically create jobs which test these
conditions are satisfied.  Until then, this is a reference for interactive testing.

## BranchSpec: */master, */JENKINS-1*

|                          | Default | Inverse | Ancestry | Default + Inverse | Inverse + Ancestry | Inverse + Inverse |
|--------------------------|---------|---------|----------|-------------------|--------------------|-------------------|
| master                   |       X |         |          |                   |                    |                X  |
| JENKINS-33827            |         |      X  |          |               X   |                    |                   |
| JENKINS-33433-branch-1   |         |      X  |          |               X   |                 X  |                   |
| JENKINS-33433-branch-2   |         |      X  |          |               X   |                 X  |                   |
| JENKINS-33433-master     |         |      X  |          |               X   |                 X  |                   |
| JENKINS-33202-x/branch-2 |         |      X  |          |               X   |                    |                   |
| JENKINS-14798            |       X |         |          |                   |                    |                X  |
| JENKINS-6203             |         |      X  |          |               X   |                    |                   |
| JENKINS-33695            |         |      X  |          |               X   |                    |                   |
| JENKINS-11337/master     |         |      X  |          |               X   |                    |                   |
| JENKINS-11337/develop    |         |      X  |          |               X   |                    |                   |
