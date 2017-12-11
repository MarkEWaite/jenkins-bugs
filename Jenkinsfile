#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def expectedText = 'This file written before checkout scm'

node {
  stage('Checkout') {
    deleteDir()
    writeFile file: 'pre-checkout-file.txt', text: expectedText
    checkout scm
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
   /* JENKINS-22795 reports that files created before checkout are removed by checkout. */
    my_check.logContains(".*${expectedText}.*", 'Existing file deleted by checkout scm')
  }
}
