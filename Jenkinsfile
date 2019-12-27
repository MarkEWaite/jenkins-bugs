#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

def branch = 'JENKINS-60591'

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

// Wait up to 90 seconds for input
// Assumes the infrastructure will push a new commit during that 90 seconds
// Assertions then check that the 'Obtained Jenkinsfile from <SHA-1>' matches the SHA-1 in the workspace
// Multibranch pipeline fails that assertion

stage('Await Input Before Checkout') {
  def answer = 'Not answered due to exception'
  try {
    timeout(time: 90, unit: 'SECONDS') {
      answer = input(id: 'Check-JENKINS-60591', message: "Ready to go (timeout in 90 seconds)?")
    }
    echo "Answer from input with timeout was: ${answer}"
  } catch(err) {
    echo "Exception ${err} ignored from input with timeout, answer was ${answer}"
  }
  echo "Final answer was: ${answer}"
}

def scmVars

node() {
  stage('Checkout') {
    scmVars = checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             // [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                    refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"]]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }
}

stage('Verify') {
  def my_check = new com.markwaite.Assert()
  my_check.logContains(".*Obtained Jenkinsfile from .*", "Missing diagnostic that reports SHA-1 of Jenkinsfile") // Confirm diagnostic message is available
  my_check.logContains(".*Obtained Jenkinsfile from ${scmVars.GIT_COMMIT}.*", "Jenkinsfile checkout using unexpected SHA-1") // Correct SHA-1 in diagnostic message
  my_check.logContains(".*Checkout has git HEAD ${scmVars.GIT_COMMIT}.*", "Missing scmVars GIT_COMMIT in log, expected SHA1 ${scmVars.GIT_COMMIT}") // Correct SHA-1 in ant command output
}
