#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: 'JENKINS-48818']],
                browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
                doGenerateSubmoduleConfigurations: false,
                extensions: [
                    [$class: 'CleanBeforeCheckout'],
                    [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/bugs/jenkins-bugs.git'],
                    [$class: 'LocalBranch', localBranch: 'JENKINS-48818'],
                    [$class: 'SubmoduleOption', disableSubmodules: false, recursiveSubmodules: false, reference: '/var/lib/git/jenkins/jenkins-pipeline-utils.git', trackingSubmodules: false],
                ],
                gitTool: scm.gitTool,
                submoduleCfg: [],
                userRemoteConfigs: [[name: 'origin', refspec: '+refs/heads/JENKINS-48818:refs/remotes/origin/JENKINS-48818', url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*user dir is.*', 'Ant script missing output')
    my_check.logContains('.*No space contents check: LICENSE.*', 'Content missing for simple submodule')
    my_check.logContains('.*Has space contents check: README.md.*', 'Content missing for submodule with space in name')
  }
}
