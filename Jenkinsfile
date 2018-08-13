#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch='JENKINS-21248'
def repo_url='https://github.com/MarkEWaite/jenkins-bugs'

node('git-1.9+') { // Needs 'git -C' argument support
  stage('Checkout') {
    // deleteDir() // Really scrub the workspace
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'SubmoduleOption', disableSubmodules: false, recursiveSubmodules: false, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', shallow: true, timeout: 7, trackingSubmodules: false],
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
    my_check.logContains('.*Add distinctive message in submodule README.*', 'Distinctive 1st commit message not found')
    my_check.logDoesNotContain('.*Reduce title length.*', 'Distinctive 2nd commit message found')
  }
}
