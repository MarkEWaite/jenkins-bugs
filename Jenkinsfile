pipeline {
  agent {
    label '!windows'
  }
  stages {
    stage('Checkout') {
      steps {
        milestone(ordinal:1, label: 'Milestone One')
        checkout scm
        withAnt('ant-latest') {
          sh 'ant info'
        }
      }
    }
  }
}
