#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Poll every 13 minutes. */
properties([pipelineTriggers([pollSCM('H/13 * * * *')])])

node {
  stage('Checkout') {
    /* More complex checkout command seems to stop continuous false detection of changes */
    checkout([$class: 'GitSCM',
              branches: [[name: 'JENKINS-43687']],
              browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'LocalBranch', localBranch: '**'],
                           [$class: 'CleanCheckout'],
                           [$class: 'AuthorInChangelog']
                          ],
              userRemoteConfigs: [[name: 'bugs-origin',
                                   refspec: '+refs/heads/JENKINS-43687:refs/remotes/bugs-origin/JENKINS-43687',
                                   url: 'https://github.com/MarkEWaite/jenkins-bugs']],
            ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-43687 reports that polling did not detect changes, this checks the opposite.  */
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
