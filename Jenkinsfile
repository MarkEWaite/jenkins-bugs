pipeline {
    agent {
        label '!windows'
    }
    stages {
        stage('Build') {
            steps {
                sh 'make package'
            }
        }
        stage('Test') {
            steps {
                sh 'make check'
            }
        }
        stage('Deploy') {
            when { tag "release-*" }
            steps {
                echo 'Deploying because this commit is tagged...'
                sh 'make deploy'
            }
        }
    }
}
