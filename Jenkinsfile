#!groovy

@Library('globalPipelineLibraryMarkEWaite') // https://github.com/MarkEWaite/jenkins-pipeline-utils
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

node('git-2.30+ && git-lfs') { // Required for git LFS
  stage('Checkout') {
    def my_extensions
    echo "Repo URL is ${scm.userRemoteConfigs[0].url}"
    echo "Contains is ${scm.userRemoteConfigs[0].url.contains('github.com')}"
    echo "SCM is ${scm}"
    echo "SCM git tool is ${scm.gitTool}"
    if (scm.userRemoteConfigs[0].url.contains('github.com') && (scm.gitTool == null || !scm.gitTool.startsWith('jgit'))) {
      my_extensions = [
        [$class: 'GitLFSPull'],
        [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
        [$class: 'LocalBranch', localBranch: 'JENKINS-35687']
      ]
    } else {
      my_extensions = [
        [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
        [$class: 'LocalBranch', localBranch: 'JENKINS-35687']
      ]
    }
    checkout([$class: 'GitSCM',
              branches: [[name: '*/JENKINS-35687']],
              extensions: my_extensions,
              gitTool: scm.gitTool,
              userRemoteConfigs: scm.userRemoteConfigs,
        ]
    )
  }

  stage('Build') {
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    if (scm.userRemoteConfigs[0].url.contains('github.com')) {
      check.logContains(".*Content of this file is tracked by git large file support.*", "Tracked content not found in large file")
    }
  }

}
