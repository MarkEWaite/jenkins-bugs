#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
    agent {
        label '!windows'
    }

    stages {
        stage("Build") {
            steps {
                dir('git-step-with-defaults') {
                    deleteDir()
                    git 'https://github.com/jenkinsci/git-plugin'
                    withAnt(installation: 'ant-latest', jdk: 'jdk8') {
                        sh 'ant -f ../build.xml info'
                    }
                    logContains([expectedRegEx: '.*echo.*user dir is.*git-step-with-defaults.*',
                                 failureMessage: 'Missing expected subdirectory git-step-with-defaults'])
                    logContains([expectedRegEx: '.*echo.*git origin url .*git-step-with-defaults.* is https://github.com/jenkinsci/git-plugin',
                                 failureMessage: 'Missing expected origin url git-plugin for git-step-with-defaults'])
                }
                dir('git-step-with-https-and-branch') {
                    deleteDir()
                    git branch: 'stable-2.204',
                        url: 'https://github.com/jenkinsci/jenkins.git'
                    withAnt(installation: 'ant-latest', jdk: 'jdk8') {
                        sh 'ant -f ../build.xml info'
                    }
                    logContains([expectedRegEx: '.*echo.*user dir is.*git-step-with-https-and-branch.*',
                                 failureMessage: 'Missing expected subdirectory git-step-with-https-and-branch'])
                    logContains([expectedRegEx: '.*echo.*git origin url .*git-step-with-https-and-branch.* is https://github.com/jenkinsci/jenkins.git',
                                 failureMessage: 'Missing expected origin url jenkins.git for git-step-with-https-and-branch'])
                }

            }
        }
    }
}
