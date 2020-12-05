#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
  agent {
    label '!windows && !cloud && linux' // Need http access to Jenkins server and a /bin/bash program
  }
  tools {
    ant 'ant-latest'
  }
  stages {
    stage('Check refspec in fetch') {
      steps {
        sh 'ant info'
        deleteDir() // Require full clone on next checkout
        logContains(expectedRegEx: ".*git.*fetch.*JENKINS-56063-refspec-env-reference-not-expanded.*JENKINS-56063-refspec-env-reference-not-expanded.*",
                    failureMessage: "Expected refspec not found in git fetch")
      }
    }
  }
}
