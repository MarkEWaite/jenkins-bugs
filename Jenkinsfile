#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
    agent any
    options {
        checkoutToSubdirectory('test-subdirectory')
        quietPeriod(29)
    }

    stages {
        stage("Build") {
            options {
                timeout(time: 7, unit: 'MINUTES')
                timestamps()
            }
            steps {
                withAnt(installation: 'ant-latest', jdk: 'jdk8') {
                    sh 'ant info'
                }
                logContains([expectedRegEx: '.*echo. user dir is .*test-subdirectory.*',
                             failureMessage: 'Missing expected subdirectory'])
            }
        }
    }
}
