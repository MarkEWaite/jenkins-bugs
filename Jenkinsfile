#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-51218'
def checkout_result = {}
def last_commit_author_email = 'unknown@example.com'

node('windows') {

  stage('Checkout') {
    checkout_result = checkout([$class: 'GitSCM',
                branches: [[name: branch]],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
    last_commit_author_email = bat returnStdout: true, script: '@echo off & git log -n 1 --pretty=format:%%ae'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    // my_check.logContains(".*[*] ${branch}.*", 'Wrong branch reported')
    def checkout_author_email = checkout_result['GIT_AUTHOR_EMAIL']
    echo "Checkout result is '${checkout_result}'"
    echo "Checkout author email is '${checkout_author_email}'"
    echo "Last commit author is '${last_commit_author_email}'"
    my_check.assertCondition(last_commit_author_email != 'unknown@example.com', 'Last commit author is unknown@example.com')
    my_check.assertCondition(last_commit_author_email == 'mark.earl.waite@gmail.com', "Last commit author is '${last_commit_author_email}' instead of 'mark.earl.waite@gmail.com'")
    // if (currentBuild.number > 1) { // Don't check first build
      // my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      // my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    // }
  }
}
