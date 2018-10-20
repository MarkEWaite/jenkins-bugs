#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-47874'
def origin = 'origin'

def branch1 = 'JENKINS-47824'
def origin1 = 'origin-47824'

def branch2 = 'JENKINS-45894.branch.with.dot.in.name'
def origin2 = 'origin-45894'

node {
  stage('Checkout') {

    parallel branch: {
      dir(branch) {
	checkout([$class: 'GitSCM',
	  branches: [[name: "${origin}/${branch}"]],
	  extensions: [
	    [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
	    [$class: 'LocalBranch', localBranch: branch]],
	  gitTool: scm.gitTool,
	  userRemoteConfigs: [[name: origin, refspec: "+refs/heads/${branch}:refs/remotes/${origin}/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
      }
    }, branch1: {
      dir(branch1) {
	checkout([$class: 'GitSCM',
	  branches: [[name: "${origin1}/${branch1}"]],
	  extensions: [
	    [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
	    [$class: 'LocalBranch', localBranch: branch1]],
	  gitTool: scm.gitTool,
	  userRemoteConfigs: [[name: origin1, refspec: "+refs/heads/${branch1}:refs/remotes/${origin1}/${branch1}", url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
      }
    }, branch2: {
      dir(branch2) {
	checkout([$class: 'GitSCM',
	  branches: [[name: "${origin2}/${branch2}"]],
	  extensions: [
	    [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
	    [$class: 'LocalBranch', localBranch: branch2]],
	  gitTool: scm.gitTool,
	  userRemoteConfigs: [[name: origin2, refspec: "+refs/heads/${branch2}:refs/remotes/${origin2}/${branch2}", url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
      }
    }, monitor: {
      def my_check = new com.markwaite.Assert()
      def endTime = System.currentTimeMillis() + 3 * 1000L
      while (System.currentTimeMillis() < endTime) {
        assertCondition(!fileExists(branch + "@tmp"), "Temp dir " + branch + "@tmp" + " found in workspace")
        assertCondition(!fileExists(branch1 + "@tmp"), "Temp dir " + branch1 + "@tmp" + " found in workspace")
        assertCondition(!fileExists(branch2 + "@tmp"), "Temp dir " + branch2 + "@tmp" + " found in workspace")
        sleep(1)
      }
    }

  }

  stage('Build') {
    /* Call the ant build. */
    dir(branch) {
      def my_step = new com.markwaite.Build()
      my_step.ant 'info'
    }
    dir(branch1) {
      def my_step = new com.markwaite.Build()
      my_step.ant 'info'
    }
    dir(branch2) {
      def my_step = new com.markwaite.Build()
      my_step.ant 'info'
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-47874 reports that multi-repository checkout is incorrect.  */
    /* I don't understand what that means, so attempted this check */
    my_check.logContains(".*[*] ${branch}.*", "Missing ${branch} reference")
    my_check.logContains(".*[*] ${branch1}.*", "Missing ${branch1} reference")
    my_check.logContains(".*[*] ${branch2}.*", "Missing ${branch2} reference")
  }
}
