#!/usr/bin/env groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch='features/JENKINS-37263'
def origin='jenkins-bugs-origin'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                   name: origin,
                                   refspec: "+refs/heads/${branch}:refs/remotes/${origin}/${branch}",
                                  ]],
              branches: [[name: branch]],
              // branches: [[name: "${origin}/${branch}"]],
              extensions: [[$class: 'CloneOption',
                            honorRefspec: true,
                            noTags: true,
                            reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                            timeout: 3],
                           [$class: 'LocalBranch', localBranch: branch],
                           [$class: 'PruneStaleBranch'],
                           // [$class: 'WipeWorkspace'],
                          ],
              gitTool: 'jgit',
             ])
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant 'info'
  }

  stage('Verify') {
    def build = new com.markwaite.Build()
    def latest_sha1 = build.getSHA1("refs/remotes/${origin}/${branch}^{commit}")
    def current_sha1 = build.getSHA1('HEAD')

    echo "Latest sha1 is ${latest_sha1}"
    echo "Current sha1 is ${current_sha1}"

    if (latest_sha1 != current_sha1) {
      manager.addWarningBadge("Missed latest: ${latest_sha1}, was ${current_sha1}.")
      manager.createSummary('warning.gif').appendText("<h1>Missed latest commit ${latest_sha1}, was ${current_sha1}!</h1>", false, false, false, 'red')
      manager.buildUnstable()
    }
  }
}
