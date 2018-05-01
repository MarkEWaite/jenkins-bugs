#!groovy

// works
// @Library('globalPipelineLibraryMarkEWaite@branch#name#contains#sharp') _

// works
// @Library('globalPipelineLibraryMarkEWaiteModern@branch#name#contains#sharp') _

// ?
@Library('globalPipelineLibraryMarkEWaiteModernGitHub@branch#name#contains#sharp') _

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: 'ZD-60678']],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: 'ZD-60678']],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[refspec: '+refs/heads/ZD-60678:refs/remotes/origin/ZD-60678', url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*[*] ZD-60678.*', 'Wrong branch reported')
  }
}
