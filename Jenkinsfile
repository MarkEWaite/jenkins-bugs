library identifier: 'jenlib@jenkins-lib', retriever: modernSCM([$class: 'GitSCMSource', remote: 'https://github.com/MarkEWaite/JENKINS-61317', traits: [gitBranchDiscovery()]])

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
                    def scmValues = checkout([$class: 'GitSCM', branches: [[name: 'master']], userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/JENKINS-61317']]])
                    if (scmValues.GIT_COMMIT != '39f25e1a379bf082a3dbc3d9c217bf4ae1c5d37c') { // From https://github.com/MarkEWaite/JENKINS-61317
                        addWarningBadge id: 'bad-sha-1', text: 'Unexpected SHA-1 returned by checkout'
                        currentBuild.result = 'UNSTABLE'
                    }
                    echo "scmValues.GIT_COMMIT = ${scmValues.GIT_COMMIT}"
                }
            }
        }
    }
}
