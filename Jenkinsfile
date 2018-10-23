pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        milestone(ordinal:1, label: 'Milestone One')
        checkout scm
      }
    }
  }
}
