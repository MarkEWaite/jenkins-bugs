#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-55536'

def httpsRemoteConfig = [ url: 'https://github.com/MarkEWaite/jenkins-bugs',
                          name: 'https-origin',
                          refspec: "+refs/heads/${branch}:refs/remotes/https-origin/${branch}" ]

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [ scm.userRemoteConfigs[0], httpsRemoteConfig ]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    if (scm.gitTool == null || !scm.gitTool.startsWith("jgit")) {
      /* JGit does not log fetch operations like command line git */
      my_check.logContains(".*fetch.*refs/heads/.*:refs/remotes/origin/.*", 'Did not fetch from first origin')
      my_check.logContains(".*fetch.*refs/heads/.*:refs/remotes/https-origin/.*", 'Did not fetch from second origin')
    }
    my_check.logContains(".* origin.*aite/jenkins-bugs.*", 'Repo missing first origin')
    my_check.logContains(".* https-origin.*https://github.com/MarkEWaite/jenkins-bugs.*", 'Repo missing second origin')
  }
}
