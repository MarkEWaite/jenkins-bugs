#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Poll every 2 minutes. */
properties([pipelineTriggers([pollSCM('H/2 * * * *')])])

def use_simple_checkout_scm = false

node {
  stage('Checkout') {
    if (use_simple_checkout_scm) {
      /* Less complex checkout command has continuous false detection of changes */
      checkout scm
    } else {
      /* More complex checkout command seems to stop continuous false detection of changes */
      checkout([$class: 'GitSCM',
                branches: [[name: '*/JENKINS-43818']],
                // branches: [[name: "*/${BRANCH_SPECIFIER}"]],
                doGenerateSubmoduleConfigurations: false,
                submoduleCfg: [],
                userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs']],
              ])
    }
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-43818 reports that parameters are ignored in branch specifier.  */
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
