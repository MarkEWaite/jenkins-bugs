#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout scmGit(
        branches: [[name: BRANCH_NAME]],
        extensions: [
            cloneOption(honorRefspec: true, noTags: true, shallow: true, depth: 1),
            localBranch(BRANCH_NAME)],
        gitTool: scm.gitTool,
        userRemoteConfigs: [
            [refspec: '+refs/heads/${BRANCH_NAME}:refs/remotes/origin/${BRANCH_NAME}',
             url: 'https://github.com/MarkEWaite/jenkins-bugs.git']])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*[+]refs/heads/JENKINS-72731:refs/remotes/origin/JENKINS-72731.*', 'Refspec not found in output') // Check JENKINS-72731 set refspec in multibranch
  }
}
