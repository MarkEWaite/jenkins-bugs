#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-58587'

node {
  def firstScmVars
  def firstSHA1
  stage('First Checkout') {
    firstScmVars = checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                    refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"]]])
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info' /* Message from first checkout */
    firstSHA1 = getSHA1('HEAD')
    echo "First SHA1 is ${firstSHA1}"
  }

  def secondScmVars
  def secondSHA1
  stage('Second checkout') {
    /* Use a separate workspace */
    ws() {
      secondScmVars = checkout([$class: 'GitSCM',
		  branches: scm.branches,
		  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
			       [$class: 'LocalBranch', localBranch: branch]
			      ],
		  gitTool: scm.gitTool,
		  userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
				      refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"]]])
      def my_step = new com.markwaite.Build()
      my_step.ant 'info-second' /* Message from second checkout */
      secondSHA1 = getSHA1('HEAD')
      echo "Second SHA1 is ${secondSHA1}"
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*First git HEAD is ${firstScmVars.GIT_COMMIT}.*", "Missing firstScmVars GIT_COMMIT in first log, expected SHA1 ${firstScmVars.GIT_COMMIT}")
    my_check.logContains(".*Second git HEAD is ${secondScmVars.GIT_COMMIT}.*", "Missing secondScmVars GIT_COMMIT in second log, expected SHA1 ${secondScmVars.GIT_COMMIT}")
    my_check.logContains(".*Second git HEAD is ${secondScmVars.GIT_COMMIT}.*", "Missing secondScmVars GIT_COMMIT in second log, expected SHA1 ${secondScmVars.GIT_COMMIT}")
    my_check.assertCondition(firstSHA1 == firstScmVars.GIT_COMMIT, "first computed ${firstSHA1} !=  first returned ${firstScmVars.GIT_COMMIT}")
    my_check.assertCondition(secondSHA1 == secondScmVars.GIT_COMMIT, "second computed ${secondSHA1} !=  second returned ${secondScmVars.GIT_COMMIT}")
  }
}

def getSHA1(def commit) {
  if (isUnix()) {
    sha1 = sh(script: "git rev-parse ${commit}", returnStdout: true)
  } else {
    // Windows treats caret as special character, must escape it
    if (commit.contains("^")) {
      commit = commit.replace("^", "^^")
    }
    // Windows returns command line before sha1 unless we ECHO OFF prior
    sha1 = bat(script: "@ECHO OFF && git rev-parse ${commit}", returnStdout: true)
  }
  // Remove white space
  sha1 = sha1.replaceAll("\\s", "")
  if (sha1.length() > 40) {
    sha1 = sha1.substring(sha1.length() - 40)
  }
  return sha1
}
