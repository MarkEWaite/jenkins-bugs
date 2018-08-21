pipeline {
  agent any
  environment {
    name='ビルド番号をインクリメント' // Japanese text
  }
  stages {
    stage('display name') {
      steps {
        echo "Environment name is ${env.name}"
        withAnt(installation: 'ant-latest') {
          sh 'ant info'
        }
      }
    }
  }
}
