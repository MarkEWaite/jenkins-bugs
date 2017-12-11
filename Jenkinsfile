#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
      branches: scm.branches,
      extensions: [
        [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
        [$class: 'LocalBranch', localBranch: '**'],
        [$class: 'PruneStaleBranch']
      ],
      gitTool: scm.gitTool,
      userRemoteConfigs: [
        [refspec: '+refs/heads/JENKINS-37727:refs/remotes/origin/JENKINS-37727' +
                  ' ' +
                  '+refs/heads/has-slash/*:refs/remotes/origin/has-slash/*',
         url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-37727 reports too many branches in repo.  */
    my_check.logContains(".*The file .* counts 1 branch.*", "Too many JENKINS-37727 references")
  }
}
