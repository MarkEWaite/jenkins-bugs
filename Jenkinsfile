#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-70858'

node('git-1.8+') { // Git versions older than 1.8 don't support sparse checkout
  stage('Checkout') {
    checkout scmGit(
              branches: [[name: branch]],
              extensions: [
                  [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                  [$class: 'SparseCheckoutPaths', sparseCheckoutPaths: [[path: 'build.number'], [path: 'build.xml'], [path: 'Jenkinsfile'], [path: 'TIA-Profiles']]],
                  cleanBeforeCheckout()
              ],
              gitTool: "Default",  // JGit does not support sparse checkout, can't use scm.gitTool
              userRemoteConfigs: [
                  [refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs']
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
    my_check.logContains(".*Files list for .*", 'Info task did not report dir contents')
    my_check.logDoesNotContain(".*Files list for .* are .*CONTRIBUTING.md.*", 'Found CONTRIBUTING file that should have been excluded from sparse checkout')
    my_check.logDoesNotContain(".*Dir list for .* are .*C-dir.*", 'Found C-dir that should have been excluded from sparse checkout')
    my_check.logContains(".*Dir list for .*T.* are .*TIA-Profiles.*", 'Not found TIA-Profiles dir that should have been included in sparse checkout')
    my_check.logContains(".*Dir list for .*", 'Info tasks did not report dir contents')
  }
}
