#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
  agent {
    label '!windows && !cloud && linux' // Need http access to Jenkins server and a /bin/bash program
  }
  options {
    buildDiscarder logRotator(numToKeepStr: '20')
    skipDefaultCheckout(true)
  }
  parameters {
    choice name: 'PIPELINE_LETTER_PARAM',
           description: 'Single letter parameter',
           choices: ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',  'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']
  }
  tools {
    ant 'ant-latest'
  }
  stages {
    stage('Checkout') {
      steps {
        checkout([$class: 'GitSCM',
                  branches: scm.branches,
                  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
                  userRemoteConfigs: scm.userRemoteConfigs
                ])
        script {
          Date date = new Date();
          def sleepTime = date.getAt(Calendar.SECOND) % 4;
          echo "sleep time is ${sleepTime}"
          // Launch the freestyle job with parameters
          def buildResult = build job: '/Bugs-Individual/Bugs-30-000-to-39-999/JENKINS-33756-label-parameter-runs-twice-on-first-selected-agent',
                                  quietPeriod: 0,
                                  parameters: [
                                               string(name: 'SLEEP_TIME', value: "${sleepTime}"),
                                               string(name: 'LETTER_PARAM', value: "${PIPELINE_LETTER_PARAM}")
                                              ]
          echo "Build result is ${buildResult.result} for build number ${buildResult.number} with id ${buildResult.id}"
          buildName "#${BUILD_NUMBER} launched ${buildResult.number}"
        }
        logContains(expectedRegEx: ".*Build result is SUCCESS for build number .*",
                    failureMessage: "Launched job failed")
        // sh 'ant info'
        // logContains(expectedRegEx: ".*Count of duplicate agent use by freestyle job: 0.*",
        //             failureMessage: "Wrong count of duplicate agent use by freestyle job")
      }
    }
  }
}
