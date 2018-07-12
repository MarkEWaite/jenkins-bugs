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
            echo "pipeline GIT_COMMIT before windows ws is ${env.GIT_COMMIT}"
            ws(dir: WORKSPACE + '/windows-dir') {
              echo "pipeline GIT_COMMIT in windows ws is ${env.GIT_COMMIT}"
              bat "echo bat GIT_COMMIT in windows ws is %GIT_COMMIT%"
            }
            echo "pipeline GIT_COMMIT after windows ws is ${env.GIT_COMMIT}"
            bat "echo bat GIT_COMMIT after windows ws is %GIT_COMMIT%"
            bat 'echo hello windows from %COMPUTERNAME%'
            withAnt(installation: 'ant-latest') {
              bat 'ant info'
            }
          }
        }
        stage('linux') {
          agent {
            label 'linux'
          }
          steps {
            echo "pipeline GIT_COMMIT before linux ws is ${env.GIT_COMMIT}"
            ws(dir: WORKSPACE + '/linux-dir') {
              echo "pipeline GIT_COMMIT in linux ws is ${env.GIT_COMMIT}"
              sh "echo sh GIT_COMMIT in linux ws is %GIT_COMMIT%"
            }
            echo "pipeline GIT_COMMIT after linux ws is ${env.GIT_COMMIT}"
            sh "echo sh GIT_COMMIT after linux ws is %GIT_COMMIT%"
            echo 'Workspace after linux ws is ' + WORKSPACE
            sh 'echo hello linux from `hostname`'
            withAnt(installation: 'ant-latest') {
              sh 'ant info'
            }
          }
        }
      }
    }
  }
}
