#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 75 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '75']]])

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: 'ZD-59897']],
                extensions: [
                    [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                    [$class: 'LocalBranch', localBranch: 'ZD-59897'],
                    [$class: 'PruneStaleBranch'],
                    [$class: 'WipeWorkspace']],
                gitTool: scm.gitTool,
                userRemoteConfigs: [
                    [credentialsId: 'MarkEWaite-github-rsa-private-key-has-passphrase',
                        refspec: '+refs/heads/ZD-59897:refs/remotes/origin/ZD-59897',
                        url: 'git@github.com:MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
    // Was a verify step before, but Blue Ocean 1.5 reports it contains no steps
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*[*] ZD-59897.*', 'Wrong branch reported')
    // if (currentBuild.number > 1) { // Don't check first build
      // my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      // my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    // }
  }
}
