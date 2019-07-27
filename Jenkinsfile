#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

pipeline {
    agent any
    tools {
        ant: 'ant-latest'
    }

    stages {
        stage("Build") {
            steps {
                sh 'ant info'
                logContains([expectedRegEx: '.*java is.*',
                             failureMessage: 'Missing expected java version report'])
            }
        }
    }
}
