#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
    agent {
        label '!windows' // allow sh step
    }
    tools {
        ant 'ant-latest'
    }
    options {
        skipDefaultCheckout(true)
    }
    stages {
        stage("Checkout") {
            steps {
                echo "Branch is ${env.BRANCH_NAME}"
                echo "scm is ${scm}"
                echo "scm.userRemoteConfigs is ${scm.userRemoteConfigs}"
                echo "scm.userRemoteConfigs[0].url is ${scm.userRemoteConfigs[0].url}"
                sh "ls -alt"
                sh "env | sort"
            }
        }
    }
}
