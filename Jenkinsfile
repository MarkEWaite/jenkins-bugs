#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch="JENKINS-36507"

node("git-1.9+") { // Shallow clone fails on git versions before 1.9
  stage('Checkout') {
    checkout([$class: 'GitSCM',
	      userRemoteConfigs: [[name: 'bugs-origin',
				   refspec: "+refs/heads/${branch}:refs/remotes/bugs-origin/${branch}",
				   url: 'https://github.com/MarkEWaite/jenkins-bugs']],
	      branches: [[name: "*/${branch}"]],
	      browser: [$class: 'GithubWeb',
			repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
	      extensions: [[$class: 'AuthorInChangelog'],
			   [$class: 'CheckoutOption', timeout: 37],
			   [$class: 'CleanBeforeCheckout'],
			   [$class: 'CloneOption',
			    depth: 3,
			    honorRefspec: true,
			    noTags: true,
			    reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
			    shallow: true,
			    timeout: 3],
			   [$class: 'LocalBranch', localBranch: '**'],
			   [$class: 'PruneStaleBranch'],
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
    check.logContains(".*exec.*[*] JENKINS-36507", "Missing current branch name.") 
    check.logDoesNotContain("exec.*JENKINS-22547", "Found extra branch name JENKINS-22547.") 
  }
}
