#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: 'ZD-60033']],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
                gitTool: 'Default',
                userRemoteConfigs: [[credentialsId: 'MarkEWaite-github-username-password', refspec: '+refs/heads/ZD-60033:refs/remotes/origin/ZD-60033', url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*[*] ZD-60033.*', 'Wrong branch reported')
    // if (currentBuild.number > 1) { // Don't check first build
      // my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      // my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    // }
  }
}
