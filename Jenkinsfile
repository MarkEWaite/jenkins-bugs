pipeline {
  agent {
    label '!windows'
  }
  stages {
    stage('Checkout Stage') {
      steps {
        milestone(ordinal:2, label: 'Checkout Milestone')
        checkout scm
      }
    }
    stage('Report Stage') {
      steps {
        milestone(ordinal:3, label: 'Report Milestone')
        withAnt(installation:'ant-latest') {
          sh 'ant info'
        }
      }
    }
  }
}
