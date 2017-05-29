#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch='JENKINS-38860'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                   name: 'bugs-origin',
                                   refspec: "+refs/heads/${branch}:refs/remotes/bugs-origin/${branch}",
                                  ]],
              branches: [[name: branch]],
              browser: [$class: 'GithubWeb',
                        repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
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
                             reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
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
    /* Check exactly 1 submodule in .git/modules/tests-submodule directory */
    checkStep.logContains(".*submodule.git.count=1", "Expected submodule git dir count not found")
  }

}
