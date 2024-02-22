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
    def checkoutMap = checkout([$class: 'GitSCM',
        branches: [[name: BRANCH_NAME]],
        extensions: [
            [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
            [$class: 'LocalBranch', localBranch: BRANCH_NAME]],
        gitTool: scm.gitTool,
        userRemoteConfigs: [[refspec: "+refs/heads/${BRANCH_NAME}:refs/remotes/origin/${BRANCH_NAME}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
    def git_commit = checkoutMap['GIT_COMMIT']
    echo "After: checkoutMap[GIT_COMMIT]=${git_commit}"
    changes = changelogEntries(changeSets: currentBuild.changeSets)
  }

  stage('Build') {
    withEnv(["CHANGESET_SIZE=${changes.size()}"]) {
      /* Call the ant build. */
      def my_step = new com.markwaite.Build()
      my_step.ant 'info'
    }
  }

  stage('Verify') {
    if (currentBuild.number > 1 && changes.size() > 0) { // Don't check first build or if build has no changes
      def my_check = new com.markwaite.Assert()
      my_check.logContains('.*Author:.*<>.*', 'Empty e-mail address not found in commit messages') // Check JENKINS-48589 would have been exercised
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
