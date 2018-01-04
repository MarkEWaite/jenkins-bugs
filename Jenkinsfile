pipeline {
  agent none
  stages {
    parallel {
      stage('windows') {
        agent 'windows'
        bat 'echo hello windows'
      }
      stage('linux') {
        agent 'linux'
        sh 'echo hello linux'
      }
    }
  }
}
