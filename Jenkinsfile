#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Poll every 29 minutes. */
properties([pipelineTriggers([pollSCM('H/29 * * * *')])])

def use_simple_checkout_scm = false

node {
  stage('Checkout') {
    if (use_simple_checkout_scm) {
      /* Less complex checkout command has continuous false detection of changes */
      checkout scm
    } else {
      /* More complex checkout command seems to stop continuous false detection of changes */
      checkout([$class: 'GitSCM',
                branches: [[name: 'JENKINS-43754']],
                browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: '**'],
                             [$class: 'CleanCheckout'],
                             [$class: 'AuthorInChangelog']
                            ],
                userRemoteConfigs: [[name: 'bugs-origin',
                                     refspec: '+refs/heads/JENKINS-43754:refs/remotes/bugs-origin/JENKINS-43754',
                                     url: 'https://github.com/MarkEWaite/jenkins-bugs']],
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
    /* JENKINS-43754 reports that polling detects changes when none exist.  */
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
