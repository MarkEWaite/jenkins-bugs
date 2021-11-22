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
                sh 'env | sort ; echo parameters are ${BOOLEAN_PARAMETER}, ${STRING_PARAMETER}, and ${CHOICE_PARAMETER}'
            }
        }
    }
}
