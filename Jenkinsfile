#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'ZD-64922'
/* Intentionally using private key to allow sshagent wrapper around `ant info` */
def repoUrl = 'git@github.com:MarkEWaite/jenkins-bugs.git'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: branch]],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repoUrl]]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    /* sshagent allows multiple credentials as arguments */
    /* not required, but an interesting test case */
    sshagent(credentials: ['MarkEWaite-github-rsa-private-key-has-passphrase', 'MarkEWaite-github-rsa-private-key', 'mwaite-mark-pc1-rsa-private-key']) {
      my_step.ant 'info'
    }
  }

  stage('Verify') {
    def properties = readProperties file: 'build.number'
    def buildNumber = properties['build.number']
    echo "buildNumber is ${buildNumber}"
    def tagPrefix = "${branch}-${buildNumber}-"
    echo "tagPrefix is ${tagPrefix}"
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*new tag.*${tagPrefix}-.* ${tagPrefix}-.*", "Tag ${tagPrefix} not pushed")
  }
}
