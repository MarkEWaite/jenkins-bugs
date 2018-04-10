#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def expectedText = 'This file written before checkout scm'

node {
  stage('Checkout') {
    deleteDir()
    writeFile file: 'pre-checkout-file.txt', text: expectedText
    checkout([$class: 'GitSCM',
                branches: [[name: 'JENKINS-22795']],
                extensions: [[$class: 'CloneOption', depth: 0, honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', shallow: false]],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[refspec: '+refs/heads/JENKINS-22795:refs/remotes/origin/JENKINS-22795', url: 'https://github.com/MarkEWaite/jenkins-bugs/']]])
  }

  stage('Build') {
    /* Call the ant build. */
    withAnt(installation: 'ant-latest', jdk: 'jdk8') {
      if (isUnix()) {
        sh 'ant info'
      } else {
        bat 'ant info'
      }
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
   /* JENKINS-22795 reports that files created before checkout are removed by checkout. */
    my_check.logContains(".*${expectedText}.*", 'Existing file deleted by checkout scm')
  }
}
