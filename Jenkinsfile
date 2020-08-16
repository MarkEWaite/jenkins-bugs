#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Poll every 29 minutes. Reduce load on git server */
/* keep only last 19 builds. Reduce history retention on master */
properties([pipelineTriggers([pollSCM('H/29 * * * *')]),
           [$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '19']]])

def use_simple_checkout_scm = false
def branch = 'JENKINS-43468'

def changes

node {
  stage('Checkout') {
    if (use_simple_checkout_scm) {
      /* Less complex checkout command doesn't report continuous false detection of changes */
      checkout scm
    } else {
      /* More complex checkout command has continuous false detection of changes */
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
    changes = changelogEntries(changeSets: currentBuild.changeSets)
  }

  stage('Build') {
    withEnv(["CHANGESET_SIZE=${changes.size()}"]) {
      /* Call the ant build. */
      def my_step = new com.markwaite.Build()
      my_step.ant 'info'
    }
  }

  stage('Verify') {
    /* JENKINS-43468 reports that polling detects changes when none exist.  */
    if (currentBuild.number > 1 && changes.size() > 0) { // Skip check on first build or if build has no changes
      def my_check = new com.markwaite.Assert()
      my_check.logContains('.*Author:.*', 'Built without commit - no author')
      my_check.logContains('.*Date:.*', 'Built without commit - no date')
    }
  }
}
