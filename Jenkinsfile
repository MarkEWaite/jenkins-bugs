#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node("git-1.8+") {
  stage('Checkout') {
    checkout scm
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    // Assumes default timeout has been changed from user interface or property
    check.logDoesNotContain(".*git.* [#] timeout=10", "Default timeout used in at least one git command")
  }
}
