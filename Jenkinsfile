#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout scm
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[name: 'bugs-origin',
                                   refspec: '+refs/heads/JENKINS-35475:refs/remotes/bugs-origin/JENKINS-35475',
                                   url: 'https://github.com/MarkEWaite/jenkins-bugs']],
              branches: [[name: 'bugs-origin/JENKINS-35475']],
              browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [
                [$class: 'AuthorInChangelog'],
                [$class: 'CleanBeforeCheckout'],
                [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git', shallow: true],
                [$class: 'RelativeTargetDirectory', relativeTargetDir: 'JENKINS-35475'],
              ],
             ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-35475 reports links and revision info is shown twice on
     * the build view when extended checkout syntax is used. */
    my_check.logContains('.*Directory contents:.*README.md.*', 'No README file')
    my_check.logContains('.*Directory contents:.*JENKINS-35475.*', 'No JENKINS-35475 directory')
  }
}
