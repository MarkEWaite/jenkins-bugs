#!groovy

@Library('globalPipelineLibraryMarkEWaite') _ // https://github.com/MarkEWaite/jenkins-pipeline-utils
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

node('git-lfs') { // Large file support equires a node with git LFS installed

  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: '*/JENKINS-35687-pub']],
              // Don't use the GitLFSPull extension
              // Rely on smudge filter to update content
              // extensions: [[$class: 'GitLFSPull']],
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
