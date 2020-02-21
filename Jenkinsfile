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
                echo "scm is ${scm.userRemoteConfigs}"
                echo "scm is ${scm.userRemoteConfigs[0].url}"
                sh "env | sort"
            }
        }
    }
}
