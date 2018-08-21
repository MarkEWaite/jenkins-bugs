pipeline {
  agent any
  environment {
    name='ビルド番号をインクリメント' // Japanese text
  }
  stages {
    stage('Display name') {
      steps {
        echo "Environment name is ${env.name}"
        withAnt(installation: 'ant-latest') {
          if (isUnix()) {
            sh 'ant info'
          } else {
            bat 'ant info'
          }
        }
      }
    }
  }
}
