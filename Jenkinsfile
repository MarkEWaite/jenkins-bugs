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
                sh "env | sort"
                script {
                    def adapted_branch_name
                    if (env.BRANCH_NAME.startsWith('PR-')) {
                        adapted_branch_name = "pr/${env.BRANCH_NAME}"
                    } else {
                        adapted_branch_name = env.BRANCH_NAME
                    }
                    checkout(
                      [ $class: 'GitSCM',
                        branches: [[name: "refs/heads/${adapted_branch_name}"]],
                        extensions: [
                          [ $class: 'CloneOption', depth: 1, honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', shallow: true],
                          [ $class: 'LocalBranch', localBranch: "${adapted_branch_name}"],
                          [ $class: 'PruneStaleBranch']
                        ],
                        gitTool: scm.gitTool,
                        userRemoteConfigs: [
                          [ refspec: "+refs/heads/*:refs/remotes/origin/* +refs/heads/pr/*:refs/remotes/origin/pr/*",
                            url: scm.userRemoteConfigs[0].url
                          ]
                        ]
                      ]
                    )
                }
                sh "ant info"
            }
        }
    }
}
