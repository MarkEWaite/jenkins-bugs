#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Poll every 7 minutes. */
properties([pipelineTriggers([pollSCM('H/2 * * * *')])])

def use_simple_checkout_scm = true

node {
  stage('Checkout') {
    if (use_simple_checkout_scm) {
      /* Less complex checkout command has continuous false detection of changes */
      checkout scm
    } else {
      /* More complex checkout command seems to stop continuous false detection of changes */
      checkout([$class: 'GitSCM',
                branches: [[name: '*/JENKINS-43468']],
                // extensions: [[$class: 'CloneOption',
                //                       honorRefspec: true,
                //                       noTags: true,
                //                       reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                //              [$class: 'LocalBranch', localBranch: '**'],
                //              [$class: 'CleanCheckout'],
                //              [$class: 'AuthorInChangelog']
                //             ],
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
    /* JENKINS-43468 reports that polling detects changes when none exist.  */
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
