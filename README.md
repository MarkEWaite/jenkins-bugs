# [JENKINS-43052](https://issues.jenkins-ci.org/browse/JENKINS-43052) Disallow check out to a sub-directory for Pipeline projects      

If the root pipeline project definition (pipeline or multi-branch
pipeline) uses the "Additional Behaviour" to checkout to a subdirectory,
the pipeline code which reads the Jenkinsfile will be unable to find
the Jenkinsfile.

If the root pipeline project definition (pipeline or multi-branch
pipeline) uses a sparse checkout and fails to include the Jenkinsfile
in the sparse checkout, the pipeline code which reads the Jenkinsfile
will be unable to find that Jenkinsfile.

The request in the bug report is to not allow checkout options which will
prevent finding the Jenkinsfile in the script directory.
