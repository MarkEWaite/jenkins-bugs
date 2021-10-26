#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([buildDiscarder(logRotator(numToKeepStr: '10'))])

def branch = 'JENKINS-66651'

def expansion = ''
def buildnum = ''

node('!windows') {
  stage('Checkout') {
      checkout([$class: 'GitSCM',
                  branches: [[name: branch]],
                  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                               [$class: 'LocalBranch', localBranch: branch]
                              ],
                  gitTool: scm.gitTool,
                  userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
      expansion = tm '${GIT_BRANCH,fullName=false}'
      buildnum = tm('${BUILD_NUMBER}')
      echo('expansion is ' + expansion)
      echo('buildnum is ' + buildnum)
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info-pipeline'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.assertCondition(buildnum ==~ '[0-9]+', "Build # was '${buildnum}', expected a number")
    my_check.assertCondition(expansion == 'master', "GIT_BRANCH was '${expansion}', expected 'master'")
  }
}
