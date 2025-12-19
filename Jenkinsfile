#!groovy

@Library(value='globalPipelineLibraryMarkEWaiteModern@v1.1.2', changelog=false) _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch='JENKINS-22547'
def repoUrl = scm.userRemoteConfigs[0].url

node('git-2.30+') { // Shallow clone fails on git versions before 1.9
  stage('Checkout') {
    checkout([$class: 'GitSCM',
	      userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}",
                                   url: repoUrl]],
	      branches: [[name: "*/${branch}"]],
	      extensions: [[$class: 'AuthorInChangelog'],
			   [$class: 'CheckoutOption', timeout: 47],
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
              gitTool: 'git', // Shallow clone not supported by our JGit implementation
	     ])
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logContains(".*git.*fetch.*timeout=3.*", "Missing clone timeout.")
    check.logContains(".*git.*checkout.*timeout=47.*", "Missing checkout timeout.")
    check.logContains(".* On branch ${branch}", "Missing local branch checkout to ${branch}.")
  }
}
