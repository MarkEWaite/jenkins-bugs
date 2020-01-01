#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
    agent {
        label '!windows'
    }

    stages {
        stage("Build") {
            steps {
                // Each example uses a separate subdirectory named based on the example
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
                    git branch: 'stable-2.x',
                        url: 'https://github.com/jenkinsci/git-client-plugin.git'
                    withAnt(installation: 'ant-latest', jdk: 'jdk8') {
                        sh 'ant -f ../build.xml info'
                    }
                    logContains([expectedRegEx: '.*echo.*user dir is.*git-step-with-https-and-branch.*',
                                 failureMessage: 'Missing expected subdirectory git-step-with-https-and-branch'])
                    logContains([expectedRegEx: '.*echo.*git origin url .*git-step-with-https-and-branch.* is https://github.com/jenkinsci/git-client-plugin.git',
                                 failureMessage: 'Missing expected origin url git-client-plugin.git for git-step-with-https-and-branch'])
                }
                dir('git-step-with-ssh-and-credential') {
                    deleteDir()
                    git credentialsId: 'MarkEWaite-github-rsa-private-key',
                        url: 'git@github.com:jenkinsci/git-client-plugin.git'
                    withAnt(installation: 'ant-latest', jdk: 'jdk8') {
                        sh 'ant -f ../build.xml info'
                    }
                    logContains([expectedRegEx: '.*echo.*user dir is.*git-step-with-ssh-and-credential.*',
                                 failureMessage: 'Missing expected subdirectory git-step-with-ssh-and-credential'])
                    logContains([expectedRegEx: '.*echo.*git origin url .*git-step-with-ssh-and-credential.* is git@github.com:jenkinsci/git-client-plugin.git',
                                 failureMessage: 'Missing expected origin url git-client-plugin.git for git-step-with-ssh-and-credential'])
                }
                dir('git-step-with-https-and-changelog') {
                    deleteDir()
                    git changelog: false,
                        url: 'https://github.com/jenkinsci/credentials-plugin.git'
                    withAnt(installation: 'ant-latest', jdk: 'jdk8') {
                        sh 'ant -f ../build.xml info'
                    }
                    logContains([expectedRegEx: '.*echo.*user dir is.*git-step-with-https-and-changelog.*',
                                 failureMessage: 'Missing expected subdirectory git-step-with-https-and-changelog'])
                    logContains([expectedRegEx: '.*echo.*git origin url .*git-step-with-https-and-changelog.* is https://github.com/jenkinsci/credentials-plugin.git',
                                 failureMessage: 'Missing expected origin url credentials-plugin.git for git-step-with-https-and-changelog'])
                }

            }
        }
    }
}
