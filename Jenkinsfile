#!groovy

@Library('globalPipelineLibraryMarkEWaiteModern@0c30065c158df07e55eeda283a7db3ff19bbfe01') _ // Checkout JENKINS-48061 SHA1 reference

import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch='JENKINS-47496'

node {
  stage('Checkout') {
    // Need explicit clone of tags for assertion
    checkout([$class: 'GitSCM',
        branches: [[name: branch]],
        browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
        doGenerateSubmoduleConfigurations: false,
        extensions: [
            [$class: 'CloneOption', honorRefspec: true, noTags: false, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
            [$class: 'LocalBranch', localBranch: branch]],
        gitTool: scm.gitTool,
        userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs']]]
    )
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-47496 reports that new tags are not built.  */
    my_check.logContains('.*JENKINS-47496-1[.]0[.][0-9]+.*', 'Missing JENKINS-47496-1.0.xx tag reference')
  }
}
