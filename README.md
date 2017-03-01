# [JENKINS-35501](https://issues.jenkins-ci.org/browse/JENKINS-35501) Git attributes file ignored

The bug report says that the contents of the .gitattributes file are
ignored.  If I use the eol=lf and eol=crlf values in the gitattributes
file and then use the ant task fixcrlf, I see no changes on Windows and
no changes on Linux.  The Windows file remains in Windows format with
CRLF, and the Linux file remains in Unix format with LF.

I saw different behavior if I set the text attribute.  I've not completed
a detailed investigation to understand that surprise.
