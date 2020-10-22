#!groovy

@Library('globalPipelineLibraryMarkEWaiteModern@0c30065c158df07e55eeda283a7db3ff19bbfe01') _ // Checkout JENKINS-48061 SHA1 reference

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch='JENKINS-64000'

node('windows') {
  stage('Checkout') {
    deleteDir() // Issue only appears on first creation of a workspace
    // Need explicit clone of tags (noTags: false) for assertion
    checkout([$class: 'GitSCM',
        branches: scm.branches,
        extensions: scm.extensions +
            [$class: 'CloneOption', honorRefspec: true, noTags: false, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
        gitTool: scm.gitTool,
        userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: scm.userRemoteConfigs[0].url]]
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
    my_check.logContains(".*${branch}-[1-9][0-9]*", 'The expected tags were not reported')
    my_check.logDoesNotContain('.*--no-tags.*jenkins-bugs.*', 'The --notags argument was detected fetching jenkins-bugs repo')
    deleteDir() // Issue only appears on first creation of a workspace
  }
}
