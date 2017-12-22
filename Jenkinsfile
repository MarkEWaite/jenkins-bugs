pipeline {
    agent any
    environment {
        TRAINING_VERSION="${GIT_COMMIT}"
    }
    stages {
        stage('Preparing') {
            steps {
                sh 'echo TRAINING_VERSION=$TRAINING_VERSION'
                sh 'env | sort'
            }
        }
    }
}
