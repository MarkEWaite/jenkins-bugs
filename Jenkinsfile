#!groovy

@Library('globalPipelineLibraryMarkEWaite') // https://github.com/MarkEWaite/jenkins-pipeline-utils
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

node {

  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: '*/JENKINS-35687']],
              extensions: [[$class: 'GitLFSPull']],
              userRemoteConfigs: [[url: 'https://github.com/markewaite/jenkins-bugs.git']],
        ]
    )
  }

  stage('Build') {
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logContains(".*Content of this file is tracked by git large file support.*", "Tracked content not found in large file")
  }


}
