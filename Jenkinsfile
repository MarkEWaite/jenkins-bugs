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
                echo "**** scm.gitTool is ${scm.gitTool} ****"
                script {
                    def scmResult = checkout(
                      scmGit(
                        branches: scm.branches, // Assumes the multibranch pipeline checkout branch definition is sufficient
                        // JENKINS-63563 says that checkout will fail without this extensions section
                        // extensions: [
                        //   cloneOption(shallow: true, honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'),
                        //   localBranch(env.BRANCH_NAME),
                        //   pruneStaleBranch(),
                        // ],
                        // Use reference repo for speed improvement and data reduction
                        extensions: [
                          cloneOption(shallow: true, honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'),
                        ],
                        gitTool: scm.gitTool,
                        userRemoteConfigs: scm.userRemoteConfigs // Assumes the multibranch pipeline checkout remoteconfig is sufficient
                      )
                    )
                    if (scmResult['GIT_URL'] == '') {
                        currentBuild.result = 'UNSTABLE'
                    } else {
                        echo "scmResult['GIT_URL'] = ${scmResult['GIT_URL']}" // JENKINS-65123 workaround, use return value from checkout
                    }
                }
                sh( script: 'echo shell GIT_URL is ${GIT_URL};env | sort', label: 'Report GIT_URL' ) // JENKINS-65123 notes that shell GIT_URL is empty
                sh( script: 'ant info', label: 'Info target from Apache ant' )
            }
        }
    }
}
