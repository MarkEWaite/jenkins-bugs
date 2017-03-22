#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

node('linux') {
  stage('Checkout') {
    checkout scm
  }

  stage('Build') {
    sh "ls -la ${pwd()}"
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'a-subdir/info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*user dir is .*', 'Ant info output missing')
  }
}
