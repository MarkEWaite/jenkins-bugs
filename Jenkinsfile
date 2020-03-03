#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-49757'

node {
  def scmVars
  stage('Checkout') {
    scmVars = checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: scm.userRemoteConfigs[0].url]]])
              ])
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info' /* Message from first checkout */	  
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*git fetch count: 2.*", "Wrong git fetch count")
  }
}
