#!groovy

@Library(value='globalPipelineLibraryMarkEWaiteModern@v1.1', changelog=false) _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch='JENKINS-22547'

node("git-1.9+") { // Shallow clone fails on git versions before 1.9
  stage('Checkout') {
    checkout([$class: 'GitSCM',
	      userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}",
				   url: 'https://github.com/MarkEWaite/jenkins-bugs']],
	      branches: [[name: "*/${branch}"]],
	      browser: [$class: 'GithubWeb',
			repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
	      extensions: [[$class: 'AuthorInChangelog'],
			   [$class: 'CheckoutOption', timeout: 37],
			   [$class: 'CleanBeforeCheckout'],
			   [$class: 'CloneOption',
			    depth: 1,
			    honorRefspec: true,
			    noTags: true,
			    reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
			    shallow: true,
			    timeout: 3],
			   [$class: 'LocalBranch', localBranch: '**'],
			   [$class: 'PruneStaleBranch'],
			  ],
              gitTool: 'git',
	     ])
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logContains(".*git.*fetch.*timeout=3", "Missing clone timeout.")
    check.logContains(".*git.*checkout.*timeout=37", "Missing checkout timeout.")
    check.logContains(".* On branch ${branch}", "Missing local branch checkout to ${branch}.")
  }
}
