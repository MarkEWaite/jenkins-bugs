pipeline {
    agent {
        label '!windows'
    }

    options {
        durabilityHint('PERFORMANCE_OPTIMIZED')
    }

    parameters {
        file description: 'Uploaded file parameter to test JENKINS-47333', name: 'test-JENKINS-47333'
    }

    stages {
        stage("List workspace contents") {
            steps {
                sh 'ls'
            }
        }
    }
}
