#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node('linux') {  /* Windows symlink support inconsistent at best */
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[name: 'bugs-origin',
                                   refspec: '+refs/heads/JENKINS-42882:refs/remotes/bugs-origin/JENKINS-42882',
                                   url: 'https://github.com/MarkEWaite/jenkins-bugs']],
              branches: [[name: 'bugs-origin/JENKINS-42882']],
              browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [
                [$class: 'AuthorInChangelog'],
                [$class: 'CleanBeforeCheckout'],
                [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', shallow: true],
                [$class: 'LocalBranch', localBranch: 'JENKINS-42882'],
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
    my_check.logContains('.*JENKINS-42882 reports that symlink to file in repo does not resolve.*', 'File content mssing')
  }
}
