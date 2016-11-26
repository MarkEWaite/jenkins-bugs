#!groovy

@Library('globalPipelineLibraryMarkEWaite')
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
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    // Assumes default timeout has been changed from user interface or property
    check.logDoesNotContain(".*TranslationBundleLoadingException.*", "Translation bundle loading exception thrown")
  }
}
