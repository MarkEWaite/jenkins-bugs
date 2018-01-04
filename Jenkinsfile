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
            bat 'if not exist build.xml exit 1'
          }
        }
        stage('linux') {
          agent {
            label 'linux'
          }
          steps {
            sh 'echo hello linux from `hostname`'
            sh '[ -f build.xml ] || exit 1'
          }
        }
      }
    }
  }
}
