#!groovy

// https://github.com/MarkEWaite/jenkins-pipeline-utils
@Library(value='globalPipelineLibraryMarkEWaite', changelog=false) _

import com.markwaite.Assert

/* Only keep the 13 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '13']]])

node("master") {
  stage('Checkout') {
    sh 'echo Working directory is `pwd`'
    sh 'pwd'
    sh 'echo JENKINS_HOME is $JENKINS_HOME' 
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logContains(".*Working directory is ${env.JENKINS_HOME}.*", "Working dir report 1 missing")
    check.logContains("Working directory is ${env.JENKINS_HOME}.*", "Working dir report 2 missing")
    check.logContains("${env.JENKINS_HOME}.*", "Working dir report 3 missing")
    check.logContains("JENKINS_HOME is ${env.JENKINS_HOME}.*", "Working dir report 4 missing")
  }
}
