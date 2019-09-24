#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch = 'JENKINS-59497'

def checkout_result = {}
def expected_sha1 = 'f0fae702de30331a8ce913cdb87ac0bdf990d85f'

/* Only run on local agents, not cloud agents.  Cloud agents can't see my local git caching server */
node('linux && git-1.9+') {
  deleteDir() // Force each run to be a fresh copy
  checkout([$class: 'GitSCM',
          branches: [[name: branch]],
          extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                      ],
          gitTool: scm.gitTool,
          userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*[*] ${branch}.*", "Wrong branch reported, expected '${branch}'")
  }
}
