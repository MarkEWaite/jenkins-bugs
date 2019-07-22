#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-42597'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[name: 'bugs-origin',
                                   refspec: "+refs/heads/${branch}:refs/remotes/bugs-origin/${branch}",
                                   url: 'https://github.com/MarkEWaite/jenkins-bugs']],
              branches: [[name: "bugs-origin/${branch}"]],
              browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [
                [$class: 'AuthorInChangelog'],
                [$class: 'CleanBeforeCheckout'],
                [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                [$class: 'LocalBranch', localBranch: branch],
              ],
             ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    if (currentBuild.changeSets.size() > 0) {
      def my_check = new com.markwaite.Assert()
      /* JENKINS-42597 reports that modified files which include a '%' in
         their name are not correctly linked from the changes page.
         This does not test for correct lnks on the changes page,
         it only tests that the git diff output includes files with
         names that include '%' so that a human can check the links.
         Last checked July 22, 2019 with git plugin 4.0.0.beta11-SNAPSHOT.
         */
      my_check.logContains('.*build-100%-number.*', 'build.number file name not in diff output')
      my_check.logContains('.*build-%ABC%-number.*', 'build.number copied file name not in diff output')
      my_check.logContains('.*build-.%ABC%.-number.*', 'build.number copied file name 2 not in diff output')
    }
  }
}
