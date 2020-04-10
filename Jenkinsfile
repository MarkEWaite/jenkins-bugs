#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node('!windows && aws-ope') {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'PruneStaleTag'], // Expected to fail due to JENKINS-61869
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: scm.userRemoteConfigs])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*count of stale git tags is 1.*", "Expected 1 stale git tag. Wrong count of stale git tags")
  }
}
