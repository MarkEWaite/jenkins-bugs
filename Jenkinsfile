#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

def branch = 'JENKINS-60564'

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

stage('Await Input Before Checkout') {
  def answer = 'Not answered due to exception'
  try {
    timeout(time: 90, unit: 'SECONDS') {
      answer = input(id: 'Check-JENKINS-60564', message: "Ready to go (timeout in 90 seconds)?")
    }
    echo "Answer from input with timeout was: ${answer}"
  } catch(err) {
    echo "Exception ${err} ignored from input with timeout, answer was ${answer}"
  }
  echo "Final answer was: ${answer}"
}

def scmVars

// Need git-2.7 to understand GIT_SSH_COMMAND variable
node('git-2.7+ && !windows') { // Would be enough to be git 2.3+, but that is not defined
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
    withCredentials([sshUserPrivateKey(credentialsId: 'MarkEWaite-centos7x64-github-rsa-private-key',
                                        keyFileVariable: 'GIT_SSH_PRIVATE_KEY_FILE',
                                        passphraseVariable: 'GIT_SSH_PRIVATE_KEY_PASSPHRASE',
                                        usernameVariable: 'GIT_SSH_USERNAME')]) {
      withEnv(['GIT_SSH_PRIVATE_KEY_FILE=' + GIT_SSH_PRIVATE_KEY_FILE]) {
        /* Call the ant build. */
        def my_step = new com.markwaite.Build()
        my_step.ant 'info'
      }
    }
  }
}

stage('Verify') {
  def my_check = new com.markwaite.Assert()
  my_check.logContains(".*GIT_SSH_PRIVATE_KEY_FILE is .*[*][*]", "Missing private key file in log") // Confirm diagnostic message is available
  my_check.logDoesNotContain(".*GIT_SSH_PRIVATE_KEY_FILE is .*env.GIT_SSH_PRIVATE_KEY_FILE", "Found unexpected private key unexpanded") // Confirm diagnostic message is available
  my_check.logContains(".*GIT_SSH_USERNAME is .*[*][*]", "Missing username in log") // Confirm diagnostic message is available
  my_check.logDoesNotContain(".*GIT_SSH_USERNAME is .*env.GIT_SSH_USERNAME", "Found unexpected username unexpanded") // Confirm diagnostic message is available
}
