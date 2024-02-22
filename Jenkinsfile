#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def changes

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
        branches: [[name: BRANCH_NAME]],
        extensions: [
            [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
            [$class: 'LocalBranch', localBranch: BRANCH_NAME]],
        gitTool: scm.gitTool,
        userRemoteConfigs: [[refspec: "+refs/heads/${BRANCH_NAME}:refs/remotes/origin/${BRANCH_NAME}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*+refs/heads/JENKINS-72731:refs/remotes/origin/JENKINS-72731.*', 'Empty e-mail address not found in commit messages') // Check JENKINS-72731 set refspec in multibranch
  }
}
