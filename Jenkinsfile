#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Poll every 5 minutes. */
properties([pipelineTriggers([pollSCM('H/5 * * * *')])])

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
    /* JENKINS-41906 reports the master branch starts a build even if
     * there are no changes detected on the master branch.  This assertion
     * checks that the commits from the last 15 minutes (reported by 'ant
     * info') are empty */
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
