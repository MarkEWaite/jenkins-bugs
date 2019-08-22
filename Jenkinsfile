#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
  agent {
    label '!windows'
  }
  tools {
    ant 'ant-latest'
    git 'jgit'
  }
  stages {
    stage('Build') {
      steps {
        sh 'ant info'
        // Reasonable that env.GIT_COMMIT is not set, since there was no default checkout
        logContains(expectedRegEx: ".*Git HEAD is ${env.GIT_COMMIT}.*",
                    failureMessage: "Missing env GIT_COMMIT value '${env.GIT_COMMIT}'")
      }
    }
  }
}
