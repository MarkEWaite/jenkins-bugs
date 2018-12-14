#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-53346'

def get_commit_sha1() {
  def sha1 = "unknown"
  if (isUnix()) {
    sha1 = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
  } else {
    sha1 = bat(returnStdout: true, script: '@ECHO OFF && git rev-parse --short HEAD').trim()
  }
  return sha1
}

def do_checkout(branch, repoUrlSuffix) {
  return checkout([$class: 'GitSCM',
	  branches: [[name: branch]],
	  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: "/var/lib/git/mwaite/bugs/${repoUrlSuffix}"],
		       [$class: 'LocalBranch', localBranch: branch]
		      ],
	  gitTool: scm.gitTool,
	  userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: "https://github.com/MarkEWaite/${repoUrlSuffix}"]]])
}

node {
  def map1 = [:]
  def map2 = [:]
  stage('Checkout') {
    map1 = do_checkout(branch, 'jenkins-bugs')
    map1['shell_output'] = get_commit_sha1()
    ws() {
      masterBranch='master'
      map2 = do_checkout('master', 'git-client-plugin')
      map2['shell_output'] = get_commit_sha1()
    }
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.assertCondition(map1['GIT_COMMIT'] != map2['GIT_COMMIT'], "git commit on base branch is ${map1['GIT_COMMIT']} same as git commit ${map2['GIT_COMMIT']} on master branch")
    my_check.assertCondition(map1['shell_output'] != map2['shell_output'], "Shell git commit on base is ${map1['shell_output']} same as git commit ${map2['shell_output']} on master branch")
    my_check.logContains(".*[*] ${branch}.*", 'Wrong branch reported')
    // my_check.logDoesNotContain(".*env.GIT_.*", 'GIT environment variable unresolved') // May not be expected that env vars are set for ant scripts?
  }
}
