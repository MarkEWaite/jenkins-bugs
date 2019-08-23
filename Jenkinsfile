#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-59008'
def branchTargetName = "${branch}-project-1"
def tagTargetName = "${branch}-project-1-tag-a"

def mergeTargetName = tagTargetName

node('git-1.8+') {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              // branches: scm.branches,
              extensions: [[$class: 'CloneOption', honorRefspec: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'LocalBranch', localBranch: branch],
                           [$class: 'PreBuildMerge', options: [
                            fastForwardMode: 'FF',
                            mergeRemote: 'origin',
                            mergeStrategy: 'default',
                            mergeTarget: mergeTargetName
                           ]]
                          ],
              gitTool: scm.gitTool, // Null pointer exception when JGit implementation
              userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                  refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}" +
                                           " +refs/heads/${mergeTargetName}:refs/remotes/origin/${mergeTargetName}" +
                                           " +refs/tags/${tagTargetName}:refs/remotes/origin/tags/${tagTargetName}"
                                  ]]
            ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()

    /* Log should contain something like this:
     [echo] git log
     [exec] *   commit 76f19bc20b9e5a601da2a622c8e9c18eb552ebd9 (HEAD)
     [exec] |\  Merge: 77b1a6e 0c9f26a
     [exec] | | Author: Vojtěch-Zweibrücken-Šafařík <email.address.from.git.client.plugin.test@example.com>
     [exec] | | Date:   Sat Apr 27 14:19:58 2019 +0000
     [exec] | | 
     [exec] | |     Merge commit '0c9f26a1eda7e9a91fca11e10b17f5725ff10417' into HEAD
     [exec] | |   
     */
    my_check.logContains('.* ..  Merge: [0-9a-f]+ [0-9a-f]+.*', 'Missing merge commit')

    /* Log should contain something like this (depending on LocalBranch setting):
     [echo] git branch
     [exec] * (HEAD detached from origin/JENKINS-59008-project-1)
     unless running an older git version like git 1.7.1 on CentOS 6
    */
    my_check.logContains(".*.exec. . .*${branch}.*", 'Wrong branch name')
  }
}
