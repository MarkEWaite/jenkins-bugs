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
    checkout scm
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
    def my_check = new com.markwaite.Assert()
    /* JENKINS-42020 reports the master branch starts a build even if
     * there are no changes detected on the master branch.  This assertion
     * checks that the commits in the last changeset (reported by 'ant
     * info') are empty */
    if (currentBuild.number > 1 && changes.size() > 0) { // Only check builds with changes
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    } else {
      my_check.logDoesNotContain('.*Author:.*', 'Build started by a commit - has author line')
      my_check.logDoesNotContain('.*Date:.*', 'Build started by a commit - has date line')
    }
  }
}
