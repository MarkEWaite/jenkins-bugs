# [JENKINS-42597](https://issues.jenkins.io/browse/JENKINS-42597) - changes page doesn't URL encode '%'

A modified file is listed in the "Changes" page and has a clickable
link.  If the file name includes a '%' character, then that clickable
link does not resolve to the file, since the '%' character is not
escaped in creating the link.

Unfortunately, checking this bug requires repeatable change detection
and there appear to be one or more job types (including pipeline) where
change detection is erratic.  Sometimes a commit is reported as the
change which caused a build, and sometimes the SHA1 is different from
one build to the next build, but no change is reported.
