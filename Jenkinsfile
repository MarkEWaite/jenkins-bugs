#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

def branch='JENKINS-43052'

node {
  stage('Checkout') {
    // Checkout to JENKINS-43052 subdirectory
    // Fast form - clone subset to subdirectory
    dir("${branch}") {
      checkout([$class: 'GitSCM',
                userRemoteConfigs: [[name: 'bugs-origin-subdir',
                                     refspec: "+refs/heads/${branch}:refs/remotes/bugs-origin-subdir/${branch}",
                                     url: 'https://github.com/MarkEWaite/jenkins-bugs']],
                branches: [[name: "${branch}"]],
                browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
                extensions: [
                  [$class: 'AuthorInChangelog'],
                  [$class: 'CleanBeforeCheckout'],
                  [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '../.git', shallow: true],
                  [$class: 'LocalBranch', localBranch: "${branch}"],
                ],
               ])
    }
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    dir("${branch}") {
      my_step.ant 'info'
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*user dir is .*', 'Ant info output missing')
  }
}
