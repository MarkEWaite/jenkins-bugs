pipeline {
    agent {
        label 'windows'
    }
    options {
        timestamps()
        durabilityHint('PERFORMANCE_OPTIMIZED')
    }
    triggers {
        pollSCM('H/13 * * * *')
    }
    tools {
        ant 'ant-latest'
    }
    stages {
        stage('Test and Package') {
            steps {
                sh 'ant info'
            }
        }
    }
}
