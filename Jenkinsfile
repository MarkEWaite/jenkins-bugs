#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 17 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '17']]])

def repo_url='https://github.com/MarkEWaite/jenkins-bugs'
def branch_name='has-slash/JENKINS-29603'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: branch_name]],
              extensions: [ [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                            [$class: 'LocalBranch', localBranch: "origin/${branch_name}"]],
              gitTool: scm.gitTool,
              userRemoteConfigs: [[refspec: "+refs/heads/${branch_name}:refs/remotes/origin/${branch_name}", url: repo_url]]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-29603 reports that notifYCommit with slash in branch name is ignored.  */
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*.JENKINS-29603. build[+][+], was [1-9]+[0-9]*.*', 'No recent commit')
    }
  }
}
