#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

def minutes_between_polls = 13

properties([pipelineTriggers([pollSCM('*/' + minutes_between_polls + ' * * * *')])])

def branch = 'JENKINS-50886'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: branch]],
                doGenerateSubmoduleConfigurations: false,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant "-DMINUTES_TO_WAIT=${minutes_between_polls} info"
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*Sleeping for .* minutes.*', 'Sleep message not detected')
  }
}
