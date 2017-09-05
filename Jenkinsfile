#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node('linux') {  /* Windows symlink support inconsistent at best */
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[refspec: "+refs/heads/${scm.branch}:refs/remotes/origin/${scm.branch}",
                                   url: 'https://github.com/MarkEWaite/jenkins-bugs']],
              branches: [[name: scm.branch]],
              browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [
                [$class: 'AuthorInChangelog'],
                [$class: 'CleanBeforeCheckout'],
                [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                [$class: 'LocalBranch', localBranch: scm.branch],
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
    /* JENKINS-42882 reports that symlink to file in repo does not resolve
     * to the content of the file. */
    my_check.logContains('.*.*', 'File content not in console output')
  }
}
