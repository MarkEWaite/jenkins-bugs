#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
	      branches: [[name: 'origin/JENKINS-34309']],
	      browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
	      extensions: [[$class: 'CloneOption',
			    honorRefspec: true,
			    noTags: true,
			    reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
			    shallow: true,
			    depth: 1,
			    timeout: 13],
			   [$class: 'LocalBranch', localBranch: 'JENKINS-34309'],
			   [$class: 'AuthorInChangelog']],
	      gitTool: 'Default',
	      userRemoteConfigs: [[credentialsId: 'MarkEWaite-github-rsa-private-key',
				   name: 'origin',
				   refspec: '+refs/heads/JENKINS-34309:refs/remotes/origin/JENKINS-34309',
				   url: 'git@github.com:MarkEWaite/jenkins-bugs']]])
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
