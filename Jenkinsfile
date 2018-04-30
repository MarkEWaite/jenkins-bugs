#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 23 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '23']]])

node {
  stage('Checkout') {
    checkout(
        [$class: 'GitSCM',
            branches: [[name: 'JENKINS-37050']],
            extensions: [[$class: 'CloneOption', honorRefspec: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
            gitTool: scm.gitTool,
            userRemoteConfigs: [[refspec: '+refs/heads/JENKINS-37050:refs/remotes/origin/JENKINS-37050', url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*JENKINS-37050-tag-.*', 'Wrong tag reported')
    // if (currentBuild.number > 1) { // Don't check first build
      // my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      // my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    // }
  }
}
