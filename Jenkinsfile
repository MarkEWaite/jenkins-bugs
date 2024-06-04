#!groovy

@Library('globalPipelineLibraryMarkEWaiteModern@0c30065c158df07e55eeda283a7db3ff19bbfe01') _ // Checkout JENKINS-48061 SHA1 reference

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch='JENKINS-73229'

node('windows') {
  stage('Checkout') {
    deleteDir()
    checkout scmGit(branches: scm.branches,
                    extensions: scm.extensions + [localBranch('**')],
                    gitTool: 'Default',
                    userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}",
                                         url: scm.userRemoteConfigs[0].url]]
                   )
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-73229 reports that local branch checkout '**' does not use expected branch in multibranch Pipeline.  */
    my_check.logContains(".*${branch}*", 'The expected branch was not reported')
    deleteDir() // Issue only appears on first creation of a workspace
  }
}
