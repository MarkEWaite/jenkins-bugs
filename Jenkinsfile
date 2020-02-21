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
                // echo "scm is ${scm}"
                // echo "scm.userRemoteConfigs is ${scm.userRemoteConfigs}"
                // echo "scm.userRemoteConfigs[0].url is ${scm.userRemoteConfigs[0].url}"
                // sh "env | sort"
                echo "**** Branch is ${env.BRANCH_NAME} ****"
                checkout(
                  [ $class: 'GitSCM',
                    branches: scm.branches, // Assumes the multibranch pipeline checkout branch definition is good enough
                    extensions: [
                      [ $class: 'CloneOption', shallow: true, depth: 1, honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                      [ $class: 'LocalBranch', localBranch: env.BRANCH_NAME ],
                      [ $class: 'PruneStaleBranch' ]
                    ],
                    gitTool: scm.gitTool,
                    userRemoteConfigs: scm.userRemoteConfigs // Assumes the multibranch pipeline checkout remoteconfig is good enough
                  ]
                )
                sh "ant info"
            }
        }
    }
}
