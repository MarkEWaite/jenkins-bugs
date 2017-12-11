#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: 'origin/develop/JENKINS-29796']],
              extensions:
                [[$class: 'AuthorInChangelog'],
                 [$class: 'LocalBranch', localBranch: '**'],
                 [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                 [$class: 'CleanBeforeCheckout']],
              userRemoteConfigs:
                [[credentialsId: 'MarkEWaite-github-username-password',
                  refspec: '+refs/heads/production/JENKINS-29796:refs/remotes/origin/production/JENKINS-29796' + ' ' +
                           '+refs/heads/develop/JENKINS-29796:refs/remotes/origin/develop/JENKINS-29796',
                  url: 'https://github.com/MarkEWaite/jenkins-bugs']]])

  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-29796 reports that multiple refspecs would cause
     * poll results to be ignored.
     * assertion checks that the commits from the last 15 minutes
     * (reported by 'ant info') are empty */
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
    // Should always report user dir from 'ant info'
    my_check.logContains('.*user dir is.*', 'Build started without a commit - no date line')
  }
}
