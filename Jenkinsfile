#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 5 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '5']]])

branch = 'JENKINS-67021'

node {
  stage('Checkout') {
    // Wastes disc space by cloning all branches, checkout of one branch
    git branch: branch,
        url: 'https://github.com/MarkEWaite/jenkins-bugs.git'
    // Save disc space with single branch checkout and honor refspec on clone and use a reference repo
    // checkout([$class: 'GitSCM',
    //           branches: [[name: 'JENKINS-67021']],
    //           extensions: [[$class: 'BuildSingleRevisionOnly'],
    //                        [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
    //           gitTool: scm.gitTool,
    //           userRemoteConfigs: [[refspec: '+refs/heads/JENKINS-67021:refs/remotes/origin/JENKINS-67021', url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*JENKINS-67021.*', 'Wrong log reported')
  }
}
