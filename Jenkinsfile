#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-55284'
def sha1 = ''

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: false, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: scm.userRemoteConfigs])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
    sha1 = my_step.getSHA1('HEAD')
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*${sha1}.*JENKINS-55284-moving.*", "Wrong tag or sha1 reported, expected '${sha1}'")
  }
}
