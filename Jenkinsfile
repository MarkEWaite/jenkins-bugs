#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-54xxx'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: branch]],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch],
                             [$class: 'ChangelogToBranch', options: [compareRemote: 'origin', compareTarget: 'master']]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[refspec: "+refs/heads/*:refs/remotes/origin/*", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*[*] ${branch}.*", 'Wrong branch reported')
    if (env.CHANGE_ID) { // Only defined for branch source providers like GitHub, not simple providers like Git
      echo "CHANGE_ID is " + env.CHANGE_ID
      my_check.logContains(".*CHANGE_ID is [0-9]+.*", 'Wrong CHANGE_ID reported')
      my_check.logContains(".*CHANGE_TARGET is master.*", 'Wrong CHANGE_TARGET reported')
      my_check.logContains(".*CHANGE_BRANCH is JENKINS-54xxx.*", 'Wrong CHANGE_BRANCH reported')
      my_check.logContains(".*CHANGE_FORK is .*env.CHANGE_FORK.*", 'CHANGE_FORK reported, not expected')
      my_check.logContains(".*CHANGE_URL is .*https://github.com/MarkEWaite/jenkins-bugs.*", 'Wrong CHANGE_URL reported')
      my_check.logContains(".*CHANGE_TITLE is Jenkins 54xxx to master DO NOT MERGE.*", 'Wrong CHANGE_TITLE reported')
      my_check.logContains(".*CHANGE_AUTHOR is .*aite.*", 'Wrong CHANGE_AUTHOR reported')
      my_check.logContains(".*CHANGE_AUTHOR_DISPLAY_NAME is .*ark.*aite.*", 'Wrong CHANGE_AUTHOR_DISPLAY_NAME reported')
      my_check.logContains(".*CHANGE_AUTHOR_EMAIL is .*ark.*aite.*", 'Wrong CHANGE_AUTHOR_EMAIL reported')
    } else {
      echo "CHANGE_ID is null or empty"
      my_check.logContains(".*CHANGE_ID is .*env.CHANGE_ID.*", 'CHANGE_ID reported, not expected')
      my_check.logContains(".*CHANGE_TARGET is .*env.CHANGE_TARGET.*", 'CHANGE_TARGET reported, not expected')
      my_check.logContains(".*CHANGE_BRANCH is .*env.CHANGE_BRANCH.*", 'CHANGE_BRANCH reported, not expected')
      my_check.logContains(".*CHANGE_FORK is .*env.CHANGE_FORK.*", 'CHANGE_FORK reported, not expected')
      my_check.logContains(".*CHANGE_URL is .*env.CHANGE_URL.*", 'CHANGE_URL reported, not expected')
      my_check.logContains(".*CHANGE_TITLE is .*env.CHANGE_TITLE.*", 'CHANGE_TITLE reported, not expected')
      my_check.logContains(".*CHANGE_AUTHOR is .*env.CHANGE_AUTHOR.*", 'CHANGE_AUTHOR reported, not expected')
      my_check.logContains(".*CHANGE_AUTHOR_DISPLAY_NAME is .*env.CHANGE_AUTHOR_DISPLAY_NAME.*", 'CHANGE_AUTHOR_DISPLAY_NAME reported, not expected')
      my_check.logContains(".*CHANGE_AUTHOR_EMAIL is .*env.CHANGE_AUTHOR_EMAIL.*", 'CHANGE_AUTHOR_EMAIL reported, not expected')
    }
  }
}
