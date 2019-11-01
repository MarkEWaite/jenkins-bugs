#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def usingJGit = scm.gitTool?.startsWith('jgit')

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
        branches: [[name: BRANCH_NAME]],
        extensions: [
            [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
            [$class: 'PruneStaleBranch']],
        gitTool: scm.gitTool,
        userRemoteConfigs: [[refspec: "+refs/heads/${BRANCH_NAME}:refs/remotes/origin/${BRANCH_NAME}",
                             url: scm.userRemoteConfigs[0].url]]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    // JENKINS-18834 detected that the initial fetch does not include the prune argument.
    // That causes the initial fetch to fail in some cases.
    if (!usingJGit) {
      my_check.logDoesNotContain(".*git fetch.*((?!prune).)*${BRANCH_NAME}.*", 'Fetch found without prune')
      my_check.logContains(".*git fetch.*--prune.*${BRANCH_NAME}.*", 'No prune in the git fetch command')
    }
  }
}
