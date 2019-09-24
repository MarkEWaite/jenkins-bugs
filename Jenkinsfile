#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch = 'JENKINS-59497'

node('linux && git-1.9+') { // This specific reference syntax is limited to Unix file systems
  stage('Checkout') {
    deleteDir() // Force each run to be a fresh copy
    checkout([$class: 'GitSCM',
            branches: [[name: branch]],
            extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                         [$class: 'RelativeTargetDirectory', relativeTargetDir: 'b-subdir'],
                         [$class: 'LocalBranch', localBranch: branch],
                        ],
            gitTool: scm.gitTool,
            userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    dir('b-subdir') {
      my_step.ant 'info'
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*[*] ${branch}.*", "Wrong branch reported, expected '${branch}'")
    my_check.logContains(".*alternates is .*bugs.jenkins-bugs.git.*", "No reference repo, alternates file content missing")
    if (!scm.gitTool.startsWith('jgit')) { // JGit does not print the message
      my_check.logContains(".*Using reference repository.*", "No message using reference repository")
    }
  }
}
