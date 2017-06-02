# [JENKINS-42597](https://issues.jenkins-ci.org/browse/JENKINS-42597) - changes page doesn't URL encode '%'

A modified file is listed in the "Changes" page and has a clickable
link.  If the file name includes a '%' character, then that clickable
link does not resolve to the file, since the '%' character is not
escaped in creating the link.