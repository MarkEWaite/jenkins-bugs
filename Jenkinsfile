#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([buildDiscarder(logRotator(numToKeepStr: '10'))])

def branch = 'JENKINS-25465'

node('!windows') {
  stage('Checkout') {
      checkout([$class: 'GitSCM',
                  branches: [[name: branch]],
                  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                               [$class: 'LocalBranch', localBranch: branch]
                              ],
                  gitTool: scm.gitTool,
                  userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
      def expansion = tm '${GIT_BRANCH,fullName=false}'
      def buildnum = tm('${BUILD_NUMBER}')
      def my_check = new com.markwaite.Assert()
      my_check.assertCondition(expansion == 'master', "GIT_BRANCH was '${expansion}', expected 'master'")
      my_check.assertCondition(buildnum ==~ '[0-9]+', "Build # was '${buildnum}', expected a number")
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info-pipeline'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-25465 token macro expansion incorrect when branch name includes '/' */
    my_check.logDoesNotContain('.*GIT_REVISION.*', 'GIT_REVISION env var reported')
    my_check.logDoesNotContain('.*GIT_BRANCH:.*', 'GIT_BRANCH env var reported')
  }
}
