library identifier: 'jenlib@jenkins-lib', retriever: modernSCM([$class: 'GitSCMSource', remote: 'https://github.com/bonfy/JENKINS-61317', traits: [gitBranchDiscovery()]])

pipeline {
    options {
        skipDefaultCheckout()
    }
    agent any
    stages {
        stage('Checkout scm') {
            steps {
                script {
                    def scmValues = checkout([$class: 'GitSCM', branches: [[name: 'master']], userRemoteConfigs: [[url: 'https://github.com/bonfy/JENKINS-61317']]])
                    echo "scmValues.GIT_COMMIT = ${scmValues.GIT_COMMIT}"
                }
            }
        }
    }
}
