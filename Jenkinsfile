#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-60204'

node('git-1.8+') {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch],
                             [$class: 'SubmoduleOption', recursiveSubmodules: true]
                            ],
                gitTool: 'Default', // JGit in git client plugin does not provide fully compatible submodule support
                userRemoteConfigs: [ scm.userRemoteConfigs[0], ]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*c7dd4596c6f82a99f4e5b8e9a88cc2938f5aa455 modules-JENKINS-60204/jenkins-pipeline-utils.*", 'Missing expected submodule status')
  }
}
