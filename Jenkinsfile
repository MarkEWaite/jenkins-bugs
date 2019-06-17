#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-58049'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM', branches: [[name: 'JENKINS-58049']],
                                extensions: [
        				[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', shallow: false],
					[$class: 'LocalBranch', localBranch: 'JENKINS-58049'],
					[$class: 'SparseCheckoutPaths', sparseCheckoutPaths: [[path: 'build.number'], [path: 'build.xml'], [path: 'Jenkinsfile']]],
					[$class: 'AuthorInChangelog'],
					[$class: 'CleanBeforeCheckout']],
				gitTool: scm.gitTool,
				userRemoteConfigs: [
					[refspec: '+refs/heads/JENKINS-58049:refs/remotes/origin/JENKINS-58049', url: 'https://github.com/MarkEWaite/jenkins-bugs']
				]
		])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".* origin.*https://github.com/MarkEWaite/jenkins-bugs.*", 'Repo missing first origin')
    my_check.logContains(".* https-origin.*https://github.com/MarkEWaite/jenkins-bugs.*", 'Repo missing second origin')
  }
}
