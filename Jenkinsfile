pipeline {
  agent none
  stages {
    stage('parallel') {
      parallel {
        stage('windows') {
          agent { 
            label 'windows'
          }
          steps {
            bat 'echo hello windows from %COMPUTERNAME%'
          }
        }
        stage('linux') {
          agent {
            label 'linux'
          }
          steps {
            sh 'echo hello linux from `hostname`'
          }
        }
      }
    }
  }
}
