#!groovy

@Library('globalPipelineLibraryMarkEWaite@branch-for-checkout-in-library') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  git poll: true, changelog: true, branch: 'master', url: 'https://github.com/jenkinsci/elastic-axis-plugin.git'
  try {
    stage('Checkout') {
      // JENKINS-70540 reports 'Not a git directory' and checkout fails
      checkout changelog: false, poll: false,
        scm: scmGit(branches: [[name: 'master']], userRemoteConfigs: [[url: 'https://github.com/jenkinsci/xshell-plugin.git']])
    }
  }
}
