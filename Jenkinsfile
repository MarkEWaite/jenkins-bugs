#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

node {
  stage('Checkout') {
    // Checkout to JENKINS-43052 subdirectory
    // Fast form - clone subset to subdirectory
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[name: 'bugs-origin-subdir',
                                   refspec: '+refs/heads/JENKINS-43052:refs/remotes/bugs-origin-subdir/JENKINS-43052',
                                   url: 'https://github.com/MarkEWaite/jenkins-bugs']],
              branches: [[name: 'JENKINS-43052']],
              browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [
                [$class: 'AuthorInChangelog'],
                [$class: 'CleanBeforeCheckout'],
                [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '../.git', shallow: true],
                [$class: 'LocalBranch', localBranch: 'JENKINS-43052'],
                [$class: 'RelativeTargetDirectory', relativeTargetDir: 'JENKINS-43052'],
              ],
             ])
    checkout scm
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    dir('JENKINS-43052') {
      my_step.ant 'info'
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*user dir is .*', 'Ant info output missing')
  }
}
