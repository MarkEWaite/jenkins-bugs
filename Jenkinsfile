pipeline {
  agent none
  stages {
    stage('parallel') {
      parallel {
        stage('windows') {
          agent 'windows'
          steps {
            bat 'echo hello windows'
          }
        }
        stage('linux') {
          agent 'linux'
          steps {
            sh 'echo hello linux'
          }
        }
      }
    }
  }
}
