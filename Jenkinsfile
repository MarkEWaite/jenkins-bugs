#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-55284'
def sha1 = ''

node('!git-2.20+') {
  stage('Checkout git before 2.20') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: false, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: scm.userRemoteConfigs])
  }

  stage('Build git before 2.20') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info-before-2.20'
    sha1 = my_step.getSHA1('HEAD')
  }

  stage('Verify git before 2.20') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*${sha1}.*JENKINS-55284-moving before-2.20 .*", "Non git 2.20 wrong tag or sha1 reported, expected '${sha1}'")
  }
}

node('git-2.20+') {
  stage('Checkout git 2.20') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: false, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: scm.userRemoteConfigs])
  }

  stage('Build git 2.20') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
    sha1 = my_step.getSHA1('HEAD')
  }

  stage('Verify git 2.20') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*${sha1}.*JENKINS-55284-moving 2.20-and-later .*", "Git 2.20 wrong tag or sha1 reported, expected '${sha1}'")
  }
}
