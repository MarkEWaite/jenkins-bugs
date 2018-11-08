pipeline {
    agent {
        label '!windows'
    }
    options {
        skipDefaultCheckout(true)
        timeout(time: 4, unit: 'HOURS')
        timestamps()
        buildDiscarder(logRotator(artifactDaysToKeepStr: '2', artifactNumToKeepStr: '5', daysToKeepStr: '15', numToKeepStr: '15'))
        durabilityHint('PERFORMANCE_OPTIMIZED')
    }
    triggers {
        pollSCM('H/7 * * * *')
    }
    tools {
        ant 'ant-latest'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout(poll: true,
                         scm: [$class: 'GitSCM',
                               branches: [[name: "refs/heads/${env.BRANCH_NAME}"]],
                               extensions: [
                                            [$class: 'CheckoutOption', timeout: 3],
                                            [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                                            [$class: 'LocalBranch', localBranch: "${env.BRANCH_NAME}"],
                                            // Sparse checkout not implemented in JGit
                                            // [$class: 'SparseCheckoutPaths', sparseCheckoutPaths: [[path: 'build.xml'], [path: 'Jenkinsfile'], [path: 'build.number']]],
                                           ],
                               gitTool: scm.gitTool,
                               // gitTool: 'git', // Sparse checkout not implemented in JGit
                               userRemoteConfigs: scm.userRemoteConfigs])
            }
        }
        stage('Test and Package') {
            steps {
                sh 'ant info'
                sh 'env | sort'
            }
        }
    }
    post {
        always {
            /* Confirmed that if deleteDir is there, then multibranch pipeline will build the branch on every poll. */
            /* Confirmed that without deleteDir, then multibranch pipeline will not build the branch on every poll. */
            deleteDir()
        }
    }
}
