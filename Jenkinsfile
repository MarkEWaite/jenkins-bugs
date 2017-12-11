#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    // Slow form - clone the whole repository
    // checkout scm

    // Fast form - clone subset
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[name: 'bugs-origin',
                                   refspec: '+refs/heads/JENKINS-35475:refs/remotes/bugs-origin/JENKINS-35475',
                                   url: 'https://github.com/MarkEWaite/jenkins-bugs']],
              branches: [[name: 'bugs-origin/JENKINS-35475']],
              browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [
                [$class: 'AuthorInChangelog'],
                [$class: 'CleanBeforeCheckout'],
                [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                [$class: 'LocalBranch', localBranch: 'JENKINS-35475'],
              ],
             ])

    // Checkout to JENKINS-35475 subdirectory
    // Fast form - clone subset to subdirectory
    dir('JENKINS-35475') {
      checkout([$class: 'GitSCM',
                userRemoteConfigs: [[name: 'bugs-origin-subdir',
                                     refspec: '+refs/heads/JENKINS-35475:refs/remotes/bugs-origin-subdir/JENKINS-35475',
                                     url: 'https://github.com/MarkEWaite/jenkins-bugs']],
                branches: [[name: 'JENKINS-35475']],
                browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
                extensions: [
                  [$class: 'AuthorInChangelog'],
                  [$class: 'CleanBeforeCheckout'],
                  [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '../.git', shallow: true],
                  [$class: 'LocalBranch', localBranch: 'JENKINS-35475'],
                ],
               ])
    }
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-35475 reports links and revision info is shown twice on
     * the build view when extended checkout syntax is used. This does
     * not check the bug is fixed.
     */
    my_check.logContains('.*Directory contents:.*README.md.*', 'No README file')
    my_check.logContains('.*Directory contents:.*JENKINS-35475.Jenkinsfile.*', 'No JENKINS-35475 Jenkinsfile')
  }
}
