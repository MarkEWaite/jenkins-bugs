#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def workspace_reused = false

node('!windows') {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'PruneStaleTag'], // Expected to fail due to JENKINS-61869
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: scm.userRemoteConfigs])
    if (fileExists('workspace-use-history')) {
      workspace_reused = true
    }
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    // Assertion only relevant when a workspace is reused
    // Relies on build.xml info target writing workspace-use-history file
    if (workspace_reused) {
      def my_check = new com.markwaite.Assert()
      my_check.logContains('.*Count of stale git tags is 1$', "Expected 1 stale git tag. Wrong count of stale git tags")
    }
  }
}
