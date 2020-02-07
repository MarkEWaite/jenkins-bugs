#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
    agent {
        label '!windows'
    }
    tools {
        ant 'ant-latest'
    }
    options {
        skipDefaultCheckout(true)
    }
    stages {
        stage("Build") {
            steps {
                script {
                    results = checkout(scm: [$class: 'GitSCM',
                                             branches: [[name: 'JENKINS-26100-declarative-skipDefaultCheckout']],
                                             extensions: [
                                                          [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                                                          [$class: 'LocalBranch', localBranch: '**'],
                                                         ],
                                             gitTool: scm.gitTool,
                                             userRemoteConfigs: scm.userRemoteConfigs])
                    echo "echo reports GIT_COMMIT after checkout is ${results.GIT_COMMIT}"          // groovy string interpolation
                    sh "echo sh reports gstring GIT_COMMIT after checkout is ${results.GIT_COMMIT}" // groovy string interpolation, no shell expansion required
                    sh 'echo sh reports shell GIT_COMMIT after checkout is ${GIT_COMMIT}'           // no groovy string interpolation, rely on shell to expand GIT_COMMIT reference
                }
                sh 'ant info'
                logContains([expectedRegEx: '.*java is.*',
                             failureMessage: 'Missing expected java version report'])
                logContains([expectedRegEx: '.*echo reports GIT_COMMIT after checkout is [a-f0-9]+.*',
                             failureMessage: 'Missing echo of GIT_COMMIT'])
                logContains([expectedRegEx: '.*sh reports gstring GIT_COMMIT after checkout is [a-f0-9]+.*',
                             failureMessage: 'Missing sh groovy string of GIT_COMMIT'])
                logContains([expectedRegEx: '.*sh reports shell GIT_COMMIT after checkout is [a-f0-9]+.*',
                             failureMessage: 'Missing sh shell string of GIT_COMMIT'])
            }
        }
    }
}
