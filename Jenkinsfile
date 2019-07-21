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
  }

  def secondScmVars
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
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*First git HEAD is ${firstScmVars.GIT_COMMIT}.*", "Missing firstScmVars GIT_COMMIT in first log, expected SHA1 ${firstScmVars.GIT_COMMIT}")
    my_check.logContains(".*Second git HEAD is ${secondScmVars.GIT_COMMIT}.*", "Missing secondScmVars GIT_COMMIT in second log, expected SHA1 ${secondScmVars.GIT_COMMIT}")
  }
}
