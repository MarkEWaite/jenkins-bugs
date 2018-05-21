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
            when { tag "MHA-2018-Red-Team-release-*" }
            steps {
                echo 'Deploying because this commit is tagged...'
                sh 'make deploy'
            }
        }
    }
}
