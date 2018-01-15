#!groovy

@Library('globalPipelineLibraryMarkEWaite@JENKINS-48938-build-every-poll') _
import com.markwaite.Assert
import com.markwaite.Build

/* Poll to see bug */
properties([
    pipelineTriggers([
        [$class: "SCMTrigger", scmpoll_spec: "H/3 * * * *"],
    ])
])

node {
  stage('Checkout') {
    checkout scm
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
