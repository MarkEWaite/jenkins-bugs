pipeline {
    agent any

    parameters {
        booleanParam(defaultValue: true, description: 'True or false', name: 'booleanParamJENKINS36451')
    }

    stages {
        stage("echo") {
            when {
                expression { return params.booleanParamJENKINS36451 }
            }
            steps {
                echo "exit 1"
            }
        }
    }
}
