library identifier: 'jenlib@jenkins-lib', retriever: modernSCM([$class: 'GitSCMSource', remote: 'https://github.com/bonfy/JENKINS-61317', traits: [gitBranchDiscovery()]])

pipeline {
    options {
        skipDefaultCheckout()
    }
    agent {
        label '!windows'
    }
    stages {
        stage('Checkout scm') {
            steps {
                script {
                    def scmValues = checkout([$class: 'GitSCM', branches: [[name: 'master']], userRemoteConfigs: [[url: 'https://github.com/bonfy/JENKINS-61317']]])
                    echo "scmValues.GIT_COMMIT = ${scmValues.GIT_COMMIT}"
                    sh 'echo GIT_ env vars START; env | sort | grep GIT_ || true; echo GIT_ env vars END'
                }
            }
        }
    }
}
