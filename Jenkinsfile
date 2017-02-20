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
    if (currentBuild.number == 1) {
      /* Build 1 is usually triggered by polling, not by change */
      my_check.logDoesNotContain('.*Author:.*', 'Has author line on build 1')
      my_check.logDoesNotContain('.*Date:.*', 'Has date line on build 1')
    } else {
      /* Subsequent builds should be triggered by 1 or more changes */
      my_check.logContains('.*Author:.*', 'No author line')
      my_check.logContains('.*Date:.*', 'No date line')
    }
    for (cause in currentBuild.rawBuild.getCauses()) {
      println "'${cause.shortDescription}' caused this build"
    }
  }
}
