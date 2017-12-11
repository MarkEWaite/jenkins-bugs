#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node('linux') {
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
    /* JENKINS-46422 reports that upper case Spansih characters are mangled on checkout
     */
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*bŨĨldÕ.ÑŨmbẼr.*', 'Build number file name not in build log')
    }
  }
}
