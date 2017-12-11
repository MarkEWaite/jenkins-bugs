#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch="JENKINS-36507"

node("git-1.9+") { // Shallow clone fails on git versions before 1.9
  stage('Checkout') {
    checkout([$class: 'GitSCM',
	      userRemoteConfigs: [[name: 'origin',
				   refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}",
				   url: 'https://github.com/MarkEWaite/jenkins-bugs']],
	      branches: [[name: "*/${branch}"]],
	      browser: [$class: 'GithubWeb',
			repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
	      extensions: [[$class: 'AuthorInChangelog'],
			   [$class: 'CheckoutOption', timeout: 37],
			   [$class: 'CleanBeforeCheckout'],
			   [$class: 'CloneOption',
			    depth: 2,
			    honorRefspec: true,
			    noTags: true,
			    reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
			    shallow: true,
			    timeout: 3],
			   [$class: 'LocalBranch', localBranch: '**'],
			   [$class: 'PruneStaleBranch'],
			   [$class: 'WipeWorkspace'],
			  ]
	     ])
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logContains(".*exec.*[*] ${branch}", "Missing current branch name - ${branch}.")
    check.logDoesNotContain("exec.*JENKINS-22547", "Found extra branch name JENKINS-22547.")
  }
}
