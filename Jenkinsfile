#!groovy

// Jenkinsfile based check not feasible, since this requires an interactive
// check that the changes link is correct.

@Library('globalPipelineLibraryMarkEWaite') _ // https://github.com/MarkEWaite/jenkins-pipeline-utils
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

node {

  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: '*/JENKINS-39905']],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true]],
                userRemoteConfigs: [[url: 'https://bitbucket.org/markewaite/jenkins-bugs.git', refspec: '+refs/heads/JENKINS-39905:refs/remotes/origin/JENKINS-39905']]])
  }

  stage('Build') {
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logContains(".*user dir is.*", "Expected ant info output not found")
  }

}
