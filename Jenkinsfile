#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-55939'

node('linux && !cloud') { // Needs 'wget' in build.xml, must be able to reach server with wget
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: scm.branches,
              extensions: [ [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                            [$class: 'LocalBranch', localBranch: branch]],
              gitTool: scm.gitTool,
              userRemoteConfigs: scm.userRemoteConfigs
             ])
    sh('git clean -xffd') // Remove extraneous files
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    if (currentBuild.number > 1) {
      my_check.logContains(".*http.*/Bugs-Pipeline-Checks/.*/${branch}/.*", 'Bug ID job path not found')
      my_check.logContains('.*"lastBuiltRevision".*', 'lastBuiltRevision not found')
      my_check.logContains('.*"SHA1".*:.*".*".*', 'SHA1 of lastBuiltRevision not found')
    }
  }
}
