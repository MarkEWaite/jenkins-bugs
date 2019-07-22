#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
  agent any
  tools {
    ant 'ant-latest'
  }
  stages {
    stage('Build') {
      steps {
        sh 'ant info'
        logContains(expectedRegEx: ".*Git HEAD is ${env.GIT_COMMIT}.*",
                    failureMessage: "Missing env GIT_COMMIT value '${env.GIT_COMMIT}'")
      }
    }
  }
}
