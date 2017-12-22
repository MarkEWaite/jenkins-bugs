#!groovy

// See https://jenkins.io/doc/book/pipeline/shared-libraries/#dynamic-retrieval for more details
// TODO: This is incomplete - need to use the library, not just load it
def lib = library identifier: 'BugCheckerLibrary@master',
                  retriever: modernSCM([$class: 'GitSCMSource',
                        credentialsId: '',
                        gitTool: 'jgit',
                        id: '0c8e5141-4438-4950-960e-a292f287c035',
                        remote: 'git://github.com/MarkEWaite/jenkins-pipeline-utils.git',
                        traits: [
                            [$class: 'CloneOptionTrait', extension: [depth: 0, honorRefspec: true, noTags: false, reference: '/var/lib/git/mwaite/jenkins/jenkins-pipeline-utils.git', shallow: false]],
                            [$class: 'SubmoduleOptionTrait', extension: [disableSubmodules: true, parentCredentials: false, recursiveSubmodules: false, reference: '', trackingSubmodules: false]],
                            [$class: 'LocalBranchTrait'],
                            [$class: 'RemoteNameSCMSourceTrait', remoteName: 'pipeline-utils-origin'],
                            [$class: 'IgnoreOnPushNotificationTrait'],
                            [$class: 'PruneStaleBranchTrait'],
                            [$class: 'GitToolSCMSourceTrait', gitTool: 'jgit'],
                            [$class: 'RefSpecsSCMSourceTrait', templates: [[value: '+refs/heads/master:refs/remotes/@{remote}/master']]]]])

@Library('globalPipelineLibraryMarkEWaite') _

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
        branches: [[name: BRANCH_NAME]],
        extensions: [
            [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
            [$class: 'LocalBranch', localBranch: BRANCH_NAME]],
        gitTool: scm.gitTool,
        userRemoteConfigs: [[refspec: "+refs/heads/${BRANCH_NAME}:refs/remotes/origin/${BRANCH_NAME}", url: 'git://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    def changeLogSets = currentBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
      def entries = changeLogSets[i].items
      for (int j = 0; j < entries.length; j++) {
        def entry = entries[j]
        echo "${entry.commitId} by ${entry.author} on ${new Date(entry.timestamp)}: ${entry.msg}"
      }
    }
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
