#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
  agent {
    label '!windows && !cloud && linux' // Need http access to Jenkins server and a /bin/bash program
  }
  tools {
    ant 'ant-latest'
  }
  options {
    skipDefaultCheckout(true)
  }
  stages {
    stage('Checkout') {
      steps {
        checkout([$class: 'GitSCM',
                  branches: scm.branches,
                  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
                  userRemoteConfigs: scm.userRemoteConfigs
                ])
        sh 'ant info'
        logContains(expectedRegEx: ".*Count of duplicate agent use by freestyle job: 0.*",
                    failureMessage: "Wrong count of duplicate agent use by freestyle job")
      }
    }
  }
}
