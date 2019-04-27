#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-51638'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              // branches: [[name: "origin/${branch}"]],
              branches: scm.branches,
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           // [$class: 'LocalBranch', localBranch: branch],
                           [$class: 'PreBuildMerge', options: [
                            fastForwardMode: 'FF',
                            mergeRemote: 'origin',
                            mergeStrategy: 'default', // JENKINS-51638 - works in git plugin 3.8.0, not in 3.9.3
                            mergeTarget: "${branch}-project-1"
                           ]]
                          ],
              gitTool: scm.gitTool,
              userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                  refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"]]])

  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    if (currentBuild.number > 1) { // Don't check first build
      /* Not a good check of the bug, need better assertions */
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
