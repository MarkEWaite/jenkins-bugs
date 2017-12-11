#!groovy

@Library('globalPipelineLibraryMarkEWaiteModern@v1.1') _ // This is the bug check

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-47824'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'LocalBranch', localBranch: branch]
                          ],
              gitTool: scm.gitTool,
              userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-47824 reports that tagged pipeline shared libraries don't load.  */
    my_check.logContains('.*user dir is.*', 'Missing user dir info output')
  }
}
