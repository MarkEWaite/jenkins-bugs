#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def repo_url=scm.userRemoteConfigs[0].url

def changes

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
                  url: repo_url]]])
    changes = changelogEntries(changeSets: currentBuild.changeSets)
  }

  stage('Build') {
    withEnv(["CHANGESET_SIZE=${changes.size()}"]) {
      /* Call the ant build. */
      def my_step = new com.markwaite.Build()
      my_step.ant 'info'
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-29796 reports that multiple refspecs would cause poll results to be ignored.
     * Assertion checks that the commits from changeset (reported by 'ant info') are empty.
     */
    if (currentBuild.number > 1 && changes.size() > 0) { // Only check builds with changes
      my_check.logDoesNotContain('.*First time build.*Skipping changelog.*', 'Later build incorrectly a first time build') // JENKINS-60159
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
    // Should always report user dir from 'ant info'
    my_check.logContains('.*user dir is.*', 'Missing user dir output')
  }
}
