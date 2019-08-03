#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch='JENKINS-34309'
def repo_url = scm.userRemoteConfigs[0].url

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
	      branches: [[name: "origin/${branch}"]],
	      extensions: [[$class: 'CloneOption',
			    honorRefspec: true,
			    noTags: true,
			    reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
			    timeout: 13],
			   [$class: 'LocalBranch', localBranch: branch],
			   [$class: 'AuthorInChangelog']],
	      gitTool: scm.gitTool,
	      userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}",
				   url: repo_url ]]])
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logContains(".*[*] JENKINS-34309", "Missing JENKINS-34309 branch name.") // Confirm LocalBranch extension worked
  }
}
