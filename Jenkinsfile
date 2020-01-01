#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-26660'
def repoUrl = scm.userRemoteConfigs[0].url

node('!windows') { // sh step to call ant
  stage('Clean submodules before') {
    dir('clean-after-checkout') {
      /* Checkout then call script to add a git containing subdirectory */
      checkout([$class: 'GitSCM',
                  branches: [[name: branch]],
                  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                               [$class: 'LocalBranch', localBranch: branch],
                               [$class: 'CleanCheckout', deleteUntrackedNestedRepositories: true],
                              ],
                  gitTool: scm.gitTool,
                  userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repoUrl]]])
      withAnt(installation: 'ant-latest') {
        sh 'ant info'
      }
      /* Call checkout again, add a git containing subdirectory, count total - should only be one */
      checkout([$class: 'GitSCM',
                  branches: [[name: branch]],
                  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                               [$class: 'LocalBranch', localBranch: branch],
                               [$class: 'CleanCheckout', deleteUntrackedNestedRepositories: true],
                              ],
                  gitTool: scm.gitTool,
                  userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repoUrl]]])
      withAnt(installation: 'ant-latest') {
        sh 'ant info'
      }
    }
  }

  stage('Clean submodules after') {
    dir('clean-before-checkout') {
      checkout([$class: 'GitSCM',
                  branches: [[name: branch]],
                  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                               [$class: 'LocalBranch', localBranch: branch],
                               [$class: 'CleanBeforeCheckout', deleteUntrackedNestedRepositories: true],
                              ],
                  gitTool: scm.gitTool,
                  userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repoUrl]]])
      withAnt(installation: 'ant-latest') {
        sh 'ant info'
      }
      checkout([$class: 'GitSCM',
                  branches: [[name: branch]],
                  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                               [$class: 'LocalBranch', localBranch: branch],
                               [$class: 'CleanBeforeCheckout', deleteUntrackedNestedRepositories: true],
                              ],
                  gitTool: scm.gitTool,
                  userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repoUrl]]])
      withAnt(installation: 'ant-latest') {
        sh 'ant info'
      }
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*Directory count: 1.*", 'Wrong directory count reported')
  }
}
