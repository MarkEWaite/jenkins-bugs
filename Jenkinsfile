#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-60204'

node('git-2.30+') {
  stage('Checkout') {
    deleteDir() // force a fresh clone into workspace
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', shallow: true, depth: 1],
                             [$class: 'LocalBranch', localBranch: branch],
                             [$class: 'SubmoduleOption', recursiveSubmodules: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', shallow: true, depth: 3, trackingSubmodules: false]
                             // [$class: 'SubmoduleOption'] // Full depth clone, no recursion, no tracking submodules
                            ],
                gitTool: 'Default', // JGit in git client plugin does not provide fully compatible submodule support
                userRemoteConfigs: [
                            [name: scm.userRemoteConfigs[0].name, url: scm.userRemoteConfigs[0].url, refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"],
                          ]
                ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*9b97cf27488dc3fac489cb1e4e0803ac3e804f7b modules-JENKINS-60204/jenkins-pipeline-utils.*", 'Missing expected submodule status')
  }
}
