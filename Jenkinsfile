#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
            branches: [[name: 'JENKINS-24304']],
            extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                         [$class: 'LocalBranch', localBranch: 'JENKINS-24304']],
            gitTool: 'git-quiet', /* Configured to hide diagnostics */
            userRemoteConfigs: [[name: 'origin', refspec: '+refs/heads/JENKINS-24304:refs/remotes/origin/JENKINS-24304', url: 'git://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logDoesNotContain('.*> git .*', 'Detailed logging still visible')
  }
}
