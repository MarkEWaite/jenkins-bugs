#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

def branch = 'JENKINS-43630'

node {
  stage('Checkout') {
    checkout([
      $class: 'GitSCM',
      branches: scm.branches,
      extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
      userRemoteConfigs: [
        [name: scm.userRemoteConfigs[0].name, url: scm.userRemoteConfigs[0].url, refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"]
      ]
    ])
  }

  stage('Failing Checkout') {
    try {
      dir('fail-checkout') {
        checkout([
          $class: 'GitSCM',
          branches: scm.branches,
          extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
          userRemoteConfigs: [
            [name: scm.userRemoteConfigs[0].name, url: scm.userRemoteConfigs[0].url, refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"],
            [name: scm.userRemoteConfigs[0].name + '-without-url'] // Add a new entry with only a name and no URL
          ]
        ])
      }
    } catch(err) {
      echo "Exception ${err} ignored from checkout"
    }
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-43630 reports null pointer exception if userRemoteConfig 
     * contains only a name (without a repository). */
    my_check.logContains('.*Git repository URL 2 is an empty string in job definition.*', 'Expected GIT_URL_2 error message not found')
  }
}
