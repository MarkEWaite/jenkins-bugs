#!groovy

@Library('globalPipelineLibraryMarkEWaite@branch-for-checkout-in-library') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  // Checkout the elastic axis plugin into workspace
  // git poll: true, changelog: true, branch: 'master', url: 'https://github.com/jenkinsci/elastic-axis-plugin.git'
  git url: 'https://github.com/jenkinsci/elastic-axis-plugin.git'
  try {
    stage('Checkout') {
      // JENKINS-70540 reports 'Not a git directory' and checkout fails
      // Does not duplicate the issue.  Replaces the workspace contents with the xshell plugin
      checkout changelog: false, poll: false,
        scm: scmGit(branches: [[name: 'master']], userRemoteConfigs: [[url: 'https://github.com/jenkinsci/xshell-plugin.git']])
    }
  } catch (Exception e) {
    echo 'Checkout scm step failed with exception ' + e.toString()
  }
}
