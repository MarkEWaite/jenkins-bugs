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
                echo "**** scm.userRemoteConfigs[0] is ${scm.userRemoteConfigs[0]} ****"
                checkout(
                  [ $class: 'GitSCM',
                    branches: scm.branches, // Assumes the multibranch pipeline checkout branch definition is sufficient
                    // extensions: [
                    //   [ $class: 'CloneOption', shallow: true, depth: 1, honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                    //   [ $class: 'LocalBranch', localBranch: env.BRANCH_NAME ],
                    //   [ $class: 'PruneStaleBranch' ]
                    // ],
                    // Add honor refspec and reference repo for speed and space improvement
                    extensions: scm.extensions + [
                      [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                    ],
                    gitTool: scm.gitTool,
                    // Default is missing narrow refspec
                    userRemoteConfigs: [ [ refspec: '+refs/heads/JENKINS-61120:refs/remotes/origin/JENKINS-61120', url: scm.userRemoteConfigs[0].url ] ]
                    // userRemoteConfigs: scm.userRemoteConfigs // Assumes the multibranch pipeline checkout remoteconfig is sufficient
                  ]
                )
                sh( script: 'ant info', label: 'Info target from Apache ant' )
            }
        }
    }
}
