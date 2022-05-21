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
          // Generate random short parameters to make it easier to group jobs in the UI
          Random random = new Random();
          int sleepTime = random.nextInt(10);
          String letters = 'abcdefghijklmnopqrstuvwxyz';
          int letterIndex = random.nextInt(letters.length());
          String letter = letters.charAt(letterIndex);
          echo "sleep time is ${sleepTime}, letter is ${letter}"
          // Launch the freestyle job with parameters
          def buildResult = build job: '/Bugs-Individual/Bugs-30-000-to-39-999/JENKINS-33756-label-parameter-runs-twice-on-first-selected-agent',
                                  quietPeriod: 0,
                                  parameters: [
                                               string(name: 'SLEEP_TIME', value: "${sleepTime}"),
                                               string(name: 'LETTER_PARAM', value: "${letter}")
                                              ]
          echo "Build result is ${buildResult.result} for downstream build number ${buildResult.number}"
          // Include downstream job in this build name
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
