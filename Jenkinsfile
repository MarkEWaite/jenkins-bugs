pipeline {
  agent none
  environment {
    name='ビルド番号をインクリメント' // Japanese text
  }
  stages {
    stage('Unix echo non-English text') {
      agent {
        label '!windows'
      }
      steps {
        echo "Environment name is ${env.name}"
        withAnt(installation: 'ant-latest') {
          sh 'ant info'
        }
      }
    }
    stage('Windows echo non-English text') {
      agent {
        label 'windows'
      }
      steps {
        echo "Environment name is ${env.name}"
        withAnt(installation: 'ant-latest') {
          bat 'ant info'
        }
      }
    }
  }
}
