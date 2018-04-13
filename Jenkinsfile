#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def bugId = 'JENKINS-31826'
def older_sha1 = 'eebf5cb8c95c050c6b74393ab2ef8e6ac4709dab'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: older_sha1]],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
              gitTool: scm.gitTool,
              userRemoteConfigs: [[refspec: "+refs/heads/${bugId}:refs/remotes/origin/${bugId}", url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*${older_sha1}.*HEAD.*", "Wrong SHA1 checkout, expected ${older_sha1}")
    my_check.logDoesNotContain(".*HEAD -> ${bugId}.*", "Wrong SHA1 checkout, expected ${older_sha1}")
  }
}
