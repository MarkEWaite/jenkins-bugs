#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

branch='JENKINS-42597'

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
    def my_check = new com.markwaite.Assert()
    /* JENKINS-42597 reports that modified files which include a '%' in
       their name are not correctly linked from the changes page. */
    my_check.logContains('.*build-100%-number.*', 'build.number file name not in diff output')
  }
}
