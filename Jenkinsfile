#!groovy

@Library('globalPipelineLibraryMarkEWaiteModern') _

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch=scm.branches[0]

node {
  stage('Checkout') {
    deleteDir() // Start from a clean workspace
    checkout scmGit(branches: scm.branches,
                    extensions: [localBranch('**')],
		    gitTool: 'Default',
		    userRemoteConfigs: [[refspec: '+refs/heads/${branch}:refs/remotes/origin/${branch}',
		                         url: scm.userRemoteConfigs[0].url ]]
		   )
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-73229 reports that --no-tags is used even when tags are requested in the pipeline definition.  */
    my_check.logContains(".*${branch}*", "The expected branch ${branch} was not found")
    deleteDir() // End by cleaning workspace
  }
}
