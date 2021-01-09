#!groovy

// Jenkinsfile based check not feasible, since this requires a dedicated
// job configured with the expected build chooser

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

// Only one build running at a time, stop prior build if new build starts
def buildNumber = BUILD_NUMBER as int; if (buildNumber > 1) milestone(buildNumber - 1); milestone(buildNumber) // Thanks to jglick

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch="JENKINS-33827"
def repo_url = scm.userRemoteConfigs[0].url

node('master') {

  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: repo_url,
                                   name: 'jenkins-bugs-origin',
                                   refspec: "+refs/heads/${branch}:refs/remotes/jenkins-bugs-origin/${branch}",
                                  ]],
              branches: [[name: "*/${branch}"]],
              extensions: [[$class: 'AuthorInChangelog'],
                           [$class: 'CloneOption',
                            depth: 3,
                            honorRefspec: true,
                            noTags: true,
                            reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                            shallow: true,
                            timeout: 3],
                           [$class: 'LocalBranch', localBranch: '**'],
                           [$class: 'SubmoduleOption',
                            disableSubmodules: false,
                            recursiveSubmodules: true,
                            reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                            trackingSubmodules: false],
                           [$class: 'WipeWorkspace'],
                           ],
              gitTool: 'Default',
             ])
  }

  stage('Build') {
    // ant " -Dconfig.file=../config.xml count" // Valid test of bug, but pipeline job def does not include a build chooser
    def step = new com.markwaite.Build()
    step.ant "count" // Counts a file in current directory, not a valid test of the bug
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logContains(".* has 4 matching lines, 4 expected.*", "No matching line count.")
  }

}
