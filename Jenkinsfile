#!groovy

// @Library('globalPipelineLibraryMarkEWaite') _

pipeline {
    agent {
        label '!windows'
    }

    options {
        durabilityHint('This-is-a-directory')
    }

    stages {
        stage("Build") {
            steps {
                withAnt("ant-latest") {
                    sh "ant info"
                }
            }
        }
    }
}
