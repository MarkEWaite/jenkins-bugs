#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

def branch = 'JENKINS-60591'

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

@NonCPS
def printSCM() {
  env.getEnvironment().each { name, value -> echo "envName: $name -> envValue $value" }
  echo "scm is ${scm}"
  echo "scm.GIT_BRANCH is ${scm.GIT_BRANCH}"
  echo "scm.GIT_CHECKOUT_DIR is ${scm.GIT_CHECKOUT_DIR}"
  echo "scm.GIT_COMMIT is ${scm.GIT_COMMIT}"
  echo "scm.GIT_PREVIOUS_COMMIT is ${scm.GIT_PREVIOUS_COMMIT}"
  echo "scm.GIT_LOCAL_BRANCH is ${scm.GIT_LOCAL_BRANCH}"
  echo "scm.gitTool is ${scm.gitTool}"
  echo "scm.branches is ${scm.branches}"
  echo "scm.branches[0] is ${scm.branches[0]}"
  echo "scm.browser is ${scm.browser}"
  echo "scm.extensions is ${scm.extensions}"
  echo "scm.key is ${scm.key}"
  echo "scm.mergeOptions is ${scm.mergeOptions}"
  echo "scm.repositories is ${scm.repositories}"
  echo "scm.submoduleCfg is ${scm.submoduleCfg}"
  echo "scm.userRemoteConfigs is ${scm.userRemoteConfigs}"
  echo "scm.createAccountBasedOnEmail is ${scm.createAccountBasedOnEmail}"
  echo "scm.doGenerateSubmoduleConfigurations is ${scm.doGenerateSubmoduleConfigurations}"
  echo "scm.useExistingAccountWithSameEmail is ${scm.useExistingAccountWithSameEmail}"
  // echo "scm.MAX_CHANGELOG is ${scm.MAX_CHANGELOG}"
  // echo "scm.VERBOSE is ${scm.VERBOSE}"
  // echo "scm.TAG is ${scm.TAG}"
  // echo "scm.PERMISSIONS is ${scm.PERMISSIONS}"
}
printSCM()

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
    printSCM()
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
