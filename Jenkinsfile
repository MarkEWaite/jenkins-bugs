pipeline {
    options {
        skipDefaultCheckout()
    }
    agent any
    stages {
        stage('Git step with defaults') {
            steps {
                git 'https://github.com/MarkEWaite/peass-ci.git' // Fails because default remote branch is 'main' rather than 'master'
                // git branch: 'main', url: 'https://github.com/MarkEWaite/peass-ci.git' // Works because branch is stated explicitly
            }
        }
    }
}
