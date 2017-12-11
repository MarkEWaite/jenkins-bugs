#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

node {
  stage('Checkout') {
    checkout([
      $class: 'GitSCM',
      branches: scm.branches,
      userRemoteConfigs: scm.userRemoteConfigs + [name: 'origin']
    ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-43630 reports null pointer exception if userRemoteConfig 
     * contains only a name (without a repository). */
    my_check.logContains('.*Author:.*', 'Git log output not found - no author line')
    my_check.logContains('.*Date:.*', 'Git log output not found - no date line')
  }
}
