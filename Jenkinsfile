#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch="has-percent-%-JENKINS-44360"

node('windows') { // Nick Jones indicated this may be Windows specific
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                   name: 'bugs-origin',
                                   refspec: "+refs/heads/${branch}:refs/remotes/bugs-origin/${branch}",
                                  ]],
              branches: [[name: "*/${branch}"]],
              browser: [$class: 'GithubWeb',
                        repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [[$class: 'AuthorInChangelog'],
                           [$class: 'CheckoutOption', timeout: 1],
                           [$class: 'CleanCheckout'],
                           [$class: 'CloneOption', honorRefspec: true, noTags: true, timeout: 3],
                           [$class: 'LocalBranch', localBranch: '**'],
                           [$class: 'PruneStaleBranch'],
                           [$class: 'SubmoduleOption', disableSubmodules: true]
                           ],
              gitTool: scm.gitTool,
             ])
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logContains(".*[*] ${branch}", "Missing branch name ${branch}.")
  }
}
