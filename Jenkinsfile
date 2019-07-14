#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-20941'

node('git-1.8+') {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: branch]],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch],
                             [$class: 'SubmoduleOption', parentCredentials: true, recursiveSubmodules: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs-private.git', threads: 2]
                            ],
                gitTool: 'Default', // JGit does not support submodules
                userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*[*] ${branch}.*", 'Wrong branch reported')
  }
}
