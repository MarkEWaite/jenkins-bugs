pipeline {
    agent any

    parameters {
        booleanParam(defaultValue: true, description: 'True or false', name: 'booleanParamJENKINS36451')
    }

    stages {
        stage("foo") {
            steps {
                echo "flag: ${params.booleanParamJENKINS36451}"
            }
        }
    }
}
