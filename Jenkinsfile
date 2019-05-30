#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch='has-percent-%-JENKINS-44360'

node { // This once was Windows specific, but 2019-05-29 check shows it is no longer Windows specific
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                   refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}",
                                  ]],
              branches: scm.branches,
              extensions: [[$class: 'AuthorInChangelog'],
                           [$class: 'CleanCheckout'],
                           [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', timeout: 3],
                           [$class: 'LocalBranch', localBranch: branch],
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
