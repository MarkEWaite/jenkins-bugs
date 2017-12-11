#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def user_dir = 'checkout-subdir-destination'

node {
  stage('Checkout') {
    dir(user_dir) {
      checkout scm
    }
  }

  stage('Build') {
    dir(user_dir) {
      /* Call the ant build. */
      def my_step = new com.markwaite.Build()
      my_step.ant 'info'
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-47646 reports that tagging from Jenkins UI fails.  */
    my_check.logContains(".*user dir is .*${user_dir}.*", 'Build not in expected subdirectory')
  }
}
