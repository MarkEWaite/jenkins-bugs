pipeline {
  agent none
  options {
    durabilityHint('PERFORMANCE_OPTIMIZED')
  }
  environment {
    name='ビルド番号をインクリメント and “Ω” should be a greek uppercase omega letter enclosed in quotation marks.' // Japanese text
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
