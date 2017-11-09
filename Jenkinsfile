#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

node {
  stage('Checkout') {
    checkout scm
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-37727 reports too many branches in repo.  */
    my_check.logContains("The file 'branch-list.txt' counts 1 branch.", "Too many JENKINS-37727 references")
  }
}
