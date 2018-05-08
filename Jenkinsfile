pipeline {
    agent any

    parameters {
        booleanParam(defaultValue: true, description: 'True or false', name: 'booleanParamJENKINS36451')
    }

    stages {
        stage("echo") {
            when {
                expression { return ! params.booleanParamJENKINS36451 }
            }
            steps {
                sh 'exit 1'
            }
        }
    }
}
