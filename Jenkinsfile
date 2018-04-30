#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 23 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '23']]])

node {
  stage('Checkout') {
    /* This works */
    /*
    checkout(
        [$class: 'GitSCM',
            branches: [[name: 'JENKINS-37050-tag-7']],
            extensions: [[$class: 'CloneOption', honorRefspec: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
            gitTool: scm.gitTool,
            userRemoteConfigs: [[refspec: '+refs/heads/JENKINS-37050:refs/remotes/origin/JENKINS-37050', url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
    */
    /* This fails */
    git branch: 'JENKINS-37050-tag-7', url: 'https://github.com/MarkEWaite/jenkins-bugs'
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*tag: JENKINS-37050-tag-[0-9]+.*', 'Wrong tag reported')
  }
}
