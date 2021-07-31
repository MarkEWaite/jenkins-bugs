#!groovy

// works
// @Library('globalPipelineLibraryMarkEWaite@branch#name#contains#sharp') _

// works
@Library('globalPipelineLibraryMarkEWaiteModern@branch#name#contains#sharp') _

// fails with git plugin 3.8.0
// @Library('globalPipelineLibraryMarkEWaiteModernGitHub@branch#name#contains#sharp') _

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def changes

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: 'JENKINS-66054']],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'ChangelogToBranch', options: [compareRemote: 'origin', compareTarget: 'JENKINS-66054-older']],
                             [$class: 'LocalBranch', localBranch: 'JENKINS-66054']],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[refspec: '+refs/heads/JENKINS-66054:refs/remotes/origin/JENKINS-66054' +
                                              ' +refs/heads/JENKINS-66054-older:refs/remotes/origin/JENKINS-66054-older',
                                     url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
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
    my_check.logContains('.*[*] JENKINS-66054.*', 'Wrong branch reported')
  }
}
