#!groovy

@Library('globalPipelineLibraryMarkEWaite@v1.1') // This is the bug check

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
    /* JENKINS-47824 reports that tagged pipeline shared libraries don't load.  */
    my_check.logContains('.*user dir is:.*', 'Missing user dir info output')
  }
}
