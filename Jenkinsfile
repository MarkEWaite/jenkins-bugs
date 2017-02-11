#!groovy

@Library('globalPipelineLibraryMarkEWaite')
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
    /* JENKINS-41906 reports the master branch starts a build even if
     * there are no changes detected on the master branch.  This assertion
     * checks that the commits from the last 15 minutes (reported by 'ant
     * info') are empty */
    my_check.logDoesNotContain('.*Author:.*', 'Found an author line')
    my_check.logDoesNotContain('.*Date:.*', 'Found a date line')
  }
}
