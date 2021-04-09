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
                    if (scmValues.GIT_COMMIT != '') {
                        addInfoBadge id: 'bad-sha-1', text: 'Unexpected SHA-1 returned by checkout'scmValues.GIT_COMMIT
                        currentBuild.result = 'UNSTABLE'
                    }
                    echo "scmValues.GIT_COMMIT = ${scmValues.GIT_COMMIT}"
                }
            }
        }
    }
}
