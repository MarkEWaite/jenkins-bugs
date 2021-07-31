#!groovy

@Library('globalPipelineLibraryMarkEWaite') _

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def changes

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: 'JENKINS-66054']],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'ChangelogToBranch', options: [compareRemote: 'origin', compareTarget: 'JENKINS-66054-older']],
                             [$class: 'LocalBranch', localBranch: 'JENKINS-66054'],
                            ],
                gitTool: scm.gitTool,
                /* Must include extra refspec so that ChangelogToBranch can refer to origin/JENKINS-66054-older */
                userRemoteConfigs: [[refspec: '+refs/heads/JENKINS-66054:refs/remotes/origin/JENKINS-66054' +
                                              ' +refs/heads/JENKINS-66054-older:refs/remotes/origin/JENKINS-66054-older',
                                     url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
    changes = changelogEntries(changeSets: currentBuild.changeSets)
  }

  stage('Show Changes') {
    withEnv(["CHANGESET_SIZE=${changes.size()}"]) {
      /* Call the ant build. */
      def my_step = new com.markwaite.Build()
      my_step.ant 'info'
    }
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*[*] JENKINS-66054.*', 'Wrong branch reported')
    my_check.logContains('.*Displaying [0-9]+ git log messages in changeset for this build.*', 'Did not report number of git log messages')
    my_check.assertCondition(changes.size() == 1, "Expected 1 change, but there were ${changes.size()}")
  }
}
