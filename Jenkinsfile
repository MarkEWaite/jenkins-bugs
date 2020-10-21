#!groovy

@Library('globalPipelineLibraryMarkEWaiteModern@0c30065c158df07e55eeda283a7db3ff19bbfe01') _ // Checkout JENKINS-48061 SHA1 reference

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch='JENKINS-64000'

node {
  stage('Checkout') {
    // Need explicit clone of tags for assertion
    checkout([$class: 'GitSCM',
        branches: scm.branches,
        extensions: [
            [$class: 'CloneOption', honorRefspec: true, noTags: false, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
        ],
        gitTool: scm.gitTool,
        userRemoteConfigs: scm.userRemoteConfigsuserRemoteConfigs
    ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-64000 reports that --no-tags is used even when tags are requested in the pipeline definition.  */
    my_check.logDoesNotContain('.*--no-tags.*', 'The --notags argument was detected')
  }
}
