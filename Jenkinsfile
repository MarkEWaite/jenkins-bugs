#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
  agent {
    label '!windows' // Runs sh
  }
  tools {
    ant 'ant-latest'
  }
  stages {
    stage('Check refspec in fetch') {
      steps {
        withAnt(installation: 'ant-latest') {
          if (isUnix()) {
            sh 'ant info'
          } else {
            bat 'ant info'
          }
        }
        deleteDir() // Require full clone on next checkout
        logContains(expectedRegEx: '.*.exec. [+]refs/heads/JENKINS-56063-refspec-env-reference-not-expanded:refs/remotes/origin/JENKINS-56063-refspec-env-reference-not-expanded$',
                    failureMessage: 'Expected remote.origin.fetch not found in output')
      }
    }
  }
}
