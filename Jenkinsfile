#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build
import com.markwaite.GitUtils

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-52855'

node {
  stage('Checkout') {
    def my_utils = new com.markwaite.GitUtils()
    checkout([$class: 'GitSCM',
              branches: [[name: "${origin}/${branch}"]],
              browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [
                [$class: 'CloneOption', honorRefspec: true, noTags: true],
              ],
              gitTool: scm.gitTool,
              userRemoteConfigs: my_utils.adjustRemoteConfig(scm.userRemoteConfigs[0], branch)
             ]
            )
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    // Value of GIT_CHECKOUT_DIR should not be set in this context
    my_check.logContains(".*[*] env.GIT_CHECKOUT_DIR.*", 'GIT_CHECKOUT_DIR unexpectedly available')
  }
}
