# Jenkins bug verification public repository

Ivan Fernandez Calvo reported that a Pipeline job would not calculate
the changelog using a base branch for the first build.  A Freestyle
job will compute a changelog for the first build when given a base
branch.

It may be that the default clone of the narrow refspec makes the
baseline branch "invisible" in the Pipeline job that is computing the
changelog.
