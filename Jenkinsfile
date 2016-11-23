#!groovy

// Jenkinsfile based check not feasible, since this requires an interactive
// check that the changes link is correct

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

node {

  stage('Checkout') {

    checkout([$class: 'GitSCM',
	      branches: [[name: 'origin/JENKINS-39905']],
	      browser: [$class: 'BitbucketWeb', repoUrl: 'https://bitbucket.org/markewaite/jenkins-bugs'],
	      extensions: [
		  [$class: 'CloneOption',
		   honorRefspec: true,
		   noTags: true,
		   reference: '/var/ilb/git/mwaite/bugs/jenkins-bugs.git',
		   timeout: 7],
		  [$class: 'PruneStaleBranch'],
		  [$class: 'AuthorInChangelog']],
	      userRemoteConfigs: [
		  [name: 'origin',
		   refspec: '+refs/heads/JENKINS-39905:refs/remotes/origin/JENKINS-39905',
		   url: 'https://bitbucket.org/markewaite/jenkins-bugs.git']]])

  stage('Build') {
    /* Call the maven build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logContains(".*user dir is.*", "Expected ant info output not found")
  }

}
