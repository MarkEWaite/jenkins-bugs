#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch='JENKINS-38860'

node('linux') { // shell script used inside the ant job
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: 'git@github.com:MarkEWaite/jenkins-bugs-private',
                                   credentialsId: 'MarkEWaite-github-rsa-private-key',
                                   name: 'bugs-private-origin',
                                   refspec: "+refs/heads/${branch}:refs/remotes/bugs-private-origin/${branch}",
                                  ]],
              branches: [[name: branch]],
              browser: [$class: 'GithubWeb',
                        repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs-private'],
              extensions: [[$class: 'AuthorInChangelog'],
                           [$class: 'CleanCheckout'],
                           [$class: 'CloneOption',
                             honorRefspec: true,
                             noTags: true,
                             timeout: 4],
                           [$class: 'LocalBranch', localBranch: branch],
                           [$class: 'PruneStaleBranch'],
                           [$class: 'SubmoduleOption',
                             disableSubmodules: false,
                             parentCredentials: true,
                             recursiveSubmodules: true,
                             reference: '/var/lib/git/mwaite/bugs/jenkins-bugs-private.git',
                             trackingSubmodules: false],
                           ],
              gitTool: 'Default', /* Submodule authentication not supported in JGit */
             ])
  }

  stage('Build') {
    /* Call the ant build. */
    def buildStep = new com.markwaite.Build()
    buildStep.ant "info"
  }

  stage('Verify') {
    def checkStep = new com.markwaite.Assert()
    /* Check that submodule README contains expected bug URL */
    checkStep.logContains(".*https://issues.jenkins-ci.org/browse/JENKINS-15103.*", "No submodule README output")
    /* Check exactly 1 submodule in tests-submodule directory */
    checkStep.logContains(".*submodule.src.count=1", "Expected submodule src dir count not found")
  }

}
