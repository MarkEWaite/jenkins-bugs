#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
  agent {
    label '!windows'
  }
  tools {
    ant 'ant-latest'
  }
  options {
    skipDefaultCheckout true
  }
  stages {
    stage('Build') {
      steps {
        echo "Branches[0] is ${scm.branches[0]}"
        checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                    refspec: '+refs/heads/JENKINS-52059-declarative:refs/remotes/origin/JENKINS-52059-declarative']]])
        sh 'ant info'
        logContains(expectedRegEx: ".*Git HEAD is ${env.GIT_COMMIT}.*",
                    failureMessage: "Missing env GIT_COMMIT value '${env.GIT_COMMIT}'")
      }
    }
  }
}
