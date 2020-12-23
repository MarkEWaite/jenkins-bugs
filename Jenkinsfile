#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
  agent any
  options {
    skipDefaultCheckout true // Do not rely on default checkout because it uses the default refspec with git multibranch provider
  }
  tools {
    ant 'ant-latest'
  }
  stages {
    stage('Check refspec in fetch') {
      steps {
        echo "scm.branches is ${scm.branches}"
        checkout(
          [ $class: 'GitSCM',
            branches: scm.branches, // Assumes the multibranch pipeline checkout branch definition is sufficient
            gitTool: scm.gitTool,
            userRemoteConfigs: [[url: scm.userRemoteConfigs[0].url,
                                refspec: '+refs/heads/JENKINS-56063-refspec-env-reference-not-expanded:refs/remotes/origin/JENKINS-56063-refspec-env-reference-not-expanded']],
            extensions: [
              [ $class: 'CloneOption', shallow: true, depth: 1, honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
            ],
          ]
        )
        script {
          withAnt(installation: 'ant-latest') {
            if (isUnix()) {
              sh 'ant info'
            } else {
              bat 'ant info'
            }
          }
        }
        deleteDir() // Require full clone on next checkout
        logContains(expectedRegEx: '.*.exec. [+]refs/heads/JENKINS-56063-refspec-env-reference-not-expanded:refs/remotes/origin/JENKINS-56063-refspec-env-reference-not-expanded$',
                    failureMessage: 'Expected remote.origin.fetch not found in output')
        logDoesNotContain(expectedRegEx: '.*[+]refs/heads/.*JOB_BASE_NAME.:refs/remotes/origin/.*JOB_BASE_NAME..*',
                    failureMessage: 'Unexpected JOB_BASE_NAME found in output')
      }
    }
  }
}
