#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Poll every 29 minutes. Reduce load on git server */
/* keep only last 20 builds. Reduce history retention on master */
properties([pipelineTriggers([pollSCM('H/29 * * * *')]),
           [$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '20']]])

def use_simple_checkout_scm = false
def branch = "JENKINS-43468"

node {
  stage('Checkout') {
    if (use_simple_checkout_scm) {
      /* Less complex checkout command has continuous false detection of changes */
      checkout scm
    } else {
      /* More complex checkout command does not have continuous false detection of changes */
      checkout([$class: 'GitSCM',
                branches: [[name: branch]],
                userRemoteConfigs: [[name: 'origin',
                                    refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}",
                                    url: 'https://github.com/MarkEWaite/jenkins-bugs']],
                extensions: [
                              [$class: 'CloneOption',
                                honorRefspec: true,
                                noTags: true,
                                reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                              [$class: 'LocalBranch', localBranch: branch],
                            ],
              ])
    }
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-43468 reports that polling detects changes when none exist.  */
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
