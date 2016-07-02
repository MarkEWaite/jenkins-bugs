[JENKINS-31828](https://issues.jenkins-ci.org/browse/JENKINS-31828) two refspecs cause polling to fail

If multiple refspecs were passed, git polling failed because the refspec
check required that all refspecs must match, instead of allowing any
one of the refspecs to match.
