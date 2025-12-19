#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-55284'
def sha1 = ''

/* Failure mode can't be tested in my config because there is no git older than 2.30 */

node('git-2.30+') {
  stage('Checkout git 2.30') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: false, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: scm.userRemoteConfigs])
  }

  stage('Build git 2.30') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
    sha1 = my_step.getSHA1('JENKINS-55284-moving')
  }

  stage('Verify git 2.30') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*${sha1}.*JENKINS-55284-moving 2.20-and-later .*", "Wrong tag or sha1 reported, expected '${sha1}'")
  }
}
