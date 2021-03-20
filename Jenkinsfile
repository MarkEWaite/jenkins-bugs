pipeline {
    options {
        timestamps()
        skipDefaultCheckout()
    }
    agent any
    stages {
        stage('Git step with defaults') {
            steps {
                git 'https://github.com/MarkEWaite/peass-ci.git' // Fails because default branch is 'main' rather than 'master'
            }
        }
    }
}
