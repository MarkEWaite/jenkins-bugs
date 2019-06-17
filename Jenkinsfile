#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-58049'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              extensions: [
                  [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                  [$class: 'SparseCheckoutPaths', sparseCheckoutPaths: [[path: 'build.number'], [path: 'build.xml'], [path: 'Jenkinsfile'], [path: 'TIA-Profiles']]],
                  [$class: 'CleanBeforeCheckout']
              ],
              gitTool: scm.gitTool,
              userRemoteConfigs: [
                  [refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs']
              ]
        ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logDoesNotContain(".*Files contents for .* are .*CONTRIBUTING.md.*", 'Found C* files that should have been excluded by sparse checkout')
    my_check.logContains(".*Files contents for .*", 'Info tasks did not report dir contents')
    my_check.logDoesNotContain(".*Dir contents for .* are .*C-dir.*", 'Found C* dir that should have been excluded by sparse checkout')
    my_check.logContains(".*Dir contents for .* are .*.TIA-Profiles.*", 'Found C* dir that should have been excluded by sparse checkout')
    my_check.logContains(".*Dir contents for .*", 'Info tasks did not report dir contents')
  }
}
