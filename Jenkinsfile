#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-56326'

node {
  def scmVars
  stage('Checkout') {
    scmVars = checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                    refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"]]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info' /* Will intentionally delay the build */
  }

  stage('Delayed checkout') {
    /* Use a separate workspace */
    ws() {
      wsVars = checkout([$class: 'GitSCM',
		  branches: scm.branches,
		  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
			       [$class: 'LocalBranch', localBranch: branch]
			      ],
		  gitTool: scm.gitTool,
		  userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
				      refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"]]])
      def my_step = new com.markwaite.Build()
      my_step.ant 'info-sleepless'
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*Sleeping git HEAD is ${scmVars.GIT_COMMIT}.*", 'Missing root GIT_COMMIT in sleeping log')
    my_check.logContains(".*Sleeping git HEAD is ${wsVars.GIT_COMMIT}.*", 'Missing root GIT_COMMIT in sleeping log')
    my_check.logContains(".*Sleepless git HEAD is ${scmVars.GIT_COMMIT}.*", 'Missing workspace GIT_COMMIT in sleepless log')
    my_check.logContains(".*Sleepless git HEAD is ${wsVars.GIT_COMMIT}.*", 'Missing workspace GIT_COMMIT in sleepless log')
  }
}
