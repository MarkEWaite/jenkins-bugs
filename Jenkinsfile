#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'has-slash/JENKINS-50401'

def checkout_result = {}
def expected_sha1 = "deadbeeffeeddeaf"

node {
  stage('Checkout') {
    checkout_result = checkout([$class: 'GitSCM',
                branches: [[name: branch]],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
    expected_sha1 = checkout_result['GIT_COMMIT']
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*[*] ${branch}.*", 'Wrong branch reported')
    my_check.logContains(".*[*] HEAD-commit-SHA1-is-${expected_sha1}.*", 'Wrong sha1 checkout at HEAD')
    my_check.logContains(".*[*] remote-commit-SHA1-is-${expected_sha1}.*", 'Remote sha1 != HEAD sha1')
  }
}
