#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

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
    /* JENKINS-42020 reports the master branch starts a build even if
     * there are no changes detected on the master branch.  This assertion
     * checks that the commits from the last 15 minutes (reported by 'ant
     * info') are empty */
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    } else {
      my_check.logDoesNotContain('.*Author:.*', 'First build started by a commit - has author line')
      my_check.logDoesNotContain('.*Date:.*', 'First build started by a commit - has date line')
    }
  }
}
