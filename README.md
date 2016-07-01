[JENKINS-29796](https://issues.jenkins-ci.org/browse/JENKINS-29796) multiple refspecs were matched by AND rather than OR

When a repository was defined with two refspecs, the git polling required
that both refspecs be matched before a change would be detected on a
branch. Both refspecs could never match, so polling would never detect
changes for repositories which contain more than 1 refspec.
