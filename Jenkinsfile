#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch='JENKINS-36451'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: 'JENKINS-36451']],
              browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              doGenerateSubmoduleConfigurations: false,
              extensions: [
                  [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                  [$class: 'LocalBranch', localBranch: branch]],
              gitTool: 'Default',
              submoduleCfg: [],
              userRemoteConfigs: [[credentialsId: 'MarkEWaite-github-username-password', refspec: '+refs/heads/JENKINS-36451:refs/remotes/origin/JENKINS-36451', url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*[*] ${branch}.*", "Wrong branch reported, expected ${branch}")
  }
}
