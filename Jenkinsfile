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
                echo "**** Branch is ${env.BRANCH_NAME} ****"
                echo "**** scm.branches is ${scm.branches} ****"
                script {
                    def scmResult = checkout(
                      [ $class: 'GitSCM',
                        branches: scm.branches, // Assumes the multibranch pipeline checkout branch definition is sufficient
                        // JENKINS-63563 says that checkout will fail without this extensions section
                        // extensions: [
                        //   [ $class: 'CloneOption', shallow: true, depth: 1, honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                        //   [ $class: 'LocalBranch', localBranch: env.BRANCH_NAME ],
                        //   [ $class: 'PruneStaleBranch' ]
                        // ],
                        gitTool: scm.gitTool,
                        userRemoteConfigs: scm.userRemoteConfigs // Assumes the multibranch pipeline checkout remoteconfig is sufficient
                      ]
                    )
                    if (scmResult['GIT_URL'] == '') {
                        currentBuild.result = 'UNSTABLE'
                    } else {
                        echo "scmResult['GIT_URL'] = ${scmResult['GIT_URL']}" // JENKINS-65123 workaround, use return value from checkout
                    }
                }
                sh( script: 'echo GIT_URL is ${GIT_URL}', label: 'Report GIT_URL' ) // JENKINS-65123
                sh( script: 'ant info', label: 'Info target from Apache ant' )
            }
        }
    }
}
