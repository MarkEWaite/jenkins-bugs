#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch='JENKINS-21248'
def repo_url=scm.userRemoteConfigs[0].url

node('git-1.9+') { // Needs 'git -C' argument support

  /* default depth should clone 1 commit */
  stage('Checkout') {
    deleteDir() // Really scrub the workspace
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'SubmoduleOption', disableSubmodules: false, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', shallow: true, trackingSubmodules: false],
                           [$class: 'LocalBranch', localBranch: branch]],
              gitTool: 'Default',
              userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repo_url]]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-21248 requests shallow clone support for submodules.  */
    my_check.logContains('.*Add distinctive message in submodule README.*', 'Default distinctive 1st commit message not found')
    my_check.logDoesNotContain('.*Reduce title length.*', 'Default distinctive 2nd commit message found')
    my_check.logDoesNotContain('.*Link from README to bug report.*', '2 - Distinctive 3rd commit message found')
  }

  /* depth 2 should clone 2 commits */
  stage('Checkout depth 2') {
    deleteDir() // Really scrub the workspace
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'SubmoduleOption', disableSubmodules: false, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', shallow: true, depth: 3, trackingSubmodules: false],
                           [$class: 'LocalBranch', localBranch: branch]],
              gitTool: 'Default',
              userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repo_url]]])
  }

  stage('Build depth 2') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify depth 2') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-21248 requests shallow clone support for submodules.  */
    my_check.logContains('.*Add distinctive message in submodule README.*', '2 - Distinctive 1st commit message not found')
    my_check.logContains('.*Reduce title length.*', '2 - Distinctive 2nd commit message not found')
    my_check.logDoesNotContain('.*Link from README to bug report.*', '2 - Distinctive 3rd commit message found')
  }

}
