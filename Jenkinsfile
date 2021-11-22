pipeline {
    options {
        skipDefaultCheckout()
    }
    agent {
        label '!windows'
    }
    parameters {
        booleanParam defaultValue: true, description: 'A boolean parameter', name: 'BOOLEAN_PARAMETER'
        string defaultValue: 'a string value', description: 'A string parameter', name: 'STRING_PARAMETER', trim: true
        choice choices: ['Choice 1', 'Choice 2', 'Choice 3'], description: 'A list of choices', name: 'CHOICE_PARAMETER'
    }
    stages {
        stage('Show values') {
            steps {
                sh '''#!/bin/bash
if [[ $BUILD_NUMBER != 1 ]]; then
  echo BOOLEAN_PARAMETER is ${BOOLEAN_PARAMETER}
  echo STRING_PARAMETER is ${STRING_PARAMETER}
  echo CHOICE_PARAMETER is ${CHOICE_PARAMETER}
fi
if [[ $CHOICE_PARAMETER =~ "Choice" ]]; then
  echo CHOICE_PARAMETER has expected value
else
  echo Failing because CHOICE_PARAMETER has unexpected value ${CHOICE_PARAMETER}
  exit 1
fi
                '''
            }
        }
    }
}
