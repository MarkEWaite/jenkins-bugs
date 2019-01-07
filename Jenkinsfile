#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'master'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch],
                             [$class: 'SubmoduleOption', parentCredentials: true, recursiveSubmodules: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', threads: 4]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: scm.userRemoteConfigs])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*submodule-dir/JENKINS-14798 .*", 'Missing JENKINS-14798 submodule')
    my_check.logContains(".*submodule-dir/JENKINS-21248 .*", 'Missing JENKINS-21248 submodule')
    my_check.logContains(".*submodule-dir/JENKINS-22547 .*", 'Missing JENKINS-22547 submodule')
    my_check.logContains(".*submodule-dir/JENKINS-22795 .*", 'Missing JENKINS-22795 submodule')
    my_check.logContains(".*submodule-dir/JENKINS-23476 .*", 'Missing JENKINS-23476 submodule')
    my_check.logContains(".*submodule-dir/JENKINS-24304 .*", 'Missing JENKINS-24304 submodule')
    my_check.logContains(".*submodule-dir/JENKINS-26660 .*", 'Missing JENKINS-26660 submodule')
  }
}
