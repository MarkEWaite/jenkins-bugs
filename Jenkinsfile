#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-60591'

node {
  def scmVars
  stage('Await Input Before Checkout') {
    timeout(time: 90, unit: 'SECONDS') {
      input(message: "Ready to go (timeout in 90 seconds)?")
    }
  }

  stage('Checkout') {
    scmVars = checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             // [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                    refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"]]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info' /* Will intentionally delay the build */
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*Checkout has git HEAD ${scmVars.GIT_COMMIT}.*", "Missing scmVars GIT_COMMIT in log, expected SHA1 ${scmVars.GIT_COMMIT}")
  }
}
