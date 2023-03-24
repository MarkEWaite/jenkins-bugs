#!groovy

pipeline {
  options {
    buildDiscarder logRotator(numToKeepStr: '10')
    disableConcurrentBuilds abortPrevious: true
    parallelsAlwaysFailFast()
    skipDefaultCheckout true
  }
  agent none
  stages {
    stage('Non-Windows') {
      agent {
        label '!windows'
      }
      steps {
        checkout scm
        sh 'ls'
      }
    }
    stage('Windows') {
      agent {
        label 'windows'
      }
      steps {
        checkout scmGit(branches: [[name: 'JENKINS-70858']],
                        extensions: [cloneOption(honorRefspec: true, noTags: true, shallow: true, depth: 1),
                        [$class: 'SparseCheckoutPaths', sparseCheckoutPaths: [[path: 'build.xml']]]],
                        gitTool: 'Default',
                        userRemoteConfigs: [[refspec: '+refs/heads/JENKINS-70858:refs/remotes/origin/JENKINS-70858',
                                             url: 'https://github.com/MarkEWaite/jenkins-bugs.git']])
        bat 'dir'
      }
    }
  }
}
