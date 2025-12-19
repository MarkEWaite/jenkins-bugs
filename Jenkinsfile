#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

def branch = 'JENKINS-60564'

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def scmVars

// Need git 2.7 or newer to understand GIT_SSH_COMMAND variable
node('git-2.30+ && !windows') { // No longer support any operating system that provides git before 2.30
  stage('Checkout') {
    deleteDir() // Remove prior checkout
    scmVars = checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                    refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"]]])
  }

  stage('Build') {
    // Pipeline does not have a git publish step, publish the hard way
    withCredentials([sshUserPrivateKey(credentialsId: 'MarkEWaite-centos7x64-github-rsa-private-key',
                                        keyFileVariable: 'GIT_SSH_PRIVATE_KEY_FILE',
                                        passphraseVariable: 'GIT_SSH_PRIVATE_KEY_PASSPHRASE',
                                        usernameVariable: 'GIT_SSH_USERNAME')]) {
      withEnv(['GIT_SSH_PRIVATE_KEY_FILE=' + GIT_SSH_PRIVATE_KEY_FILE]) {
        withAnt(installation: 'ant-latest', jdk: 'jdk21') {
          /* Call the ant build. */
          sh 'ant pipeline-info'
          sh 'ant publish'
        }
      }
    }
  }
}

stage('Verify') {
  def my_check = new com.markwaite.Assert()
  my_check.logContains(".*echo.*GIT_SSH_PRIVATE_KEY_FILE is .*[*][*].*", "Missing private key file in log") // Confirm diagnostic message is available
  my_check.logDoesNotContain(".*echo.*GIT_SSH_PRIVATE_KEY_FILE is .*env.GIT_SSH_PRIVATE_KEY_FILE.*", "Found unexpected private key unexpanded") // Confirm diagnostic message is available
  my_check.logContains(".*echo.*GIT_SSH_USERNAME is .*[*][*]", "Missing username in log") // Confirm diagnostic message is available
  my_check.logDoesNotContain(".*echo.*GIT_SSH_USERNAME is .*env.GIT_SSH_USERNAME", "Found unexpected username unexpanded") // Confirm diagnostic message is available
}
