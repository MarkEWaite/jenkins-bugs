[JENKINS-31393](https://issues.jenkins.io/browse/JENKINS-31393) - refspec ignored on initial fetch

The refspec provided in the job configuration was ignored during the
initial clone of the repository. If a non-default refspec was used,
it was expected that only the content to match that refspec would
be retrieved, yet the fetch brought the entire repository history.
