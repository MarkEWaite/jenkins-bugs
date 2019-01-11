#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-30515'
def non_existent_credentials_id = 'JENKINS-30515-non-existent-credentials-id'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: scm.userRemoteConfigs])
    ws() {
      checkout([$class: 'GitSCM',
		  branches: [[ 'master' ]],
		  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true]],
		  gitTool: scm.gitTool,
		  userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
				       credentialsId: non_existent_credentials_id,
				       name: 'non-existent-credentials-origin',
				       refspec: "+refs/heads/master:refs/remotes/non-existent-credentials-origin/master",
				      ]])
    }
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*${non_existent_credentials_id}.*", 'Non-existing credentials ID not reported')
    my_check.logContains(".*user dir is .*", 'Missing expected output')
  }
}
