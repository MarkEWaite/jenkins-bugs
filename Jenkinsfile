#!groovy

// @Library('globalPipelineLibraryMarkEWaite') _

pipeline {
    agent {
        label '!windows'
    }

    options {
        checkoutToSubdirectory('This-is-a-directory')
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
