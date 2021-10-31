#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 5 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '5']]])

branch = 'JENKINS-67021'

node {
  stage('Checkout') {
    git branch: branch,
        url: 'https://github.com/MarkEWaite/jenkins-bugs'
    sh 'env | sort'
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*JENKINS-67021.*', 'Wrong log reported')
  }
}
