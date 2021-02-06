#!groovy

@Library('globalPipelineLibraryMarkEWaite') _ // https://github.com/MarkEWaite/jenkins-pipeline-utils
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch = 'JENKINS-35687-pub'

def repo_url = scm.userRemoteConfigs[0].url

node('git-lfs && git-1.9+') { // Large file support requires a node with git LFS installed and git 1.9 or later

  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              // Don't use the GitLFSPull extension
              // Rely on smudge filter to update content
              // extensions: [[$class: 'GitLFSPull']],
              gitTool: 'Default', // JGit does not support LFS
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git']],
              userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repo_url ]],
        ]
    )
  }

  stage('Build') {
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    if (repo_url.contains('github.com')) {
      check.logContains(".*Content of this file is tracked by git large file support.*", "Tracked content not found in large file")
    }
  }

}
