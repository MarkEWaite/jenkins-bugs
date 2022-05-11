#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch='JENKINS-46054'
def repo_url='https://github.com/MarkEWaite/jenkins-bugs'

node('git-2.11+') { // Needs 'git -C' argument support
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'SubmoduleOption', recursiveSubmodules: false],
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
    /* JENKINS-46053 reports that submodule clone fails if the repo
     * URL includes '.url'.
     */
    my_check.logContains('.*Add distinctive message in submodule README.*', 'Distinctive commit message not found')
  }
}
