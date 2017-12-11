#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

def branch = 'JENKINS-15103'
def origin = "${branch}-original"
def repo = 'https://github.com/MarkEWaite/jenkins-bugs'

import java.util.Random

def random = new Random()

def implementations = [ 'git', 'jgit', 'jgitapache' ]

def tasks = [ : ]

for (int i = 0; i < implementations.size(); ++i) {
  def gitImplementation = implementations[i]
  tasks[gitImplementation] = {
    node('windows') {
      stage("Checkout ${gitImplementation}") {
        def my_check = new com.markwaite.Assert()
        if (random.nextBoolean()) { /* Randomly use pipeline native command to wipe workspace */
          deleteDir()
          my_check.assertCondition(!fileExists('.git/objects'), ".git/objects exists after ${gitImplementation} deleteDir")
        } else {
          if (currentBuild.number == 1) {
            my_check.assertCondition(!fileExists('.git/objects'), ".git/objects exists on first ${gitImplementation} build")
          } else {
            /* Will fail if a build moves from one node to another, or uses a different workspace */
            my_check.assertCondition(fileExists('.git/objects'), ".git/objects does not exist on subsequent ${gitImplementation} build")
          }
        }
        def implementation = gitImplementation == "git" ? "Default" : gitImplementation
        checkout([$class: 'GitSCM',
                  branches: [[name: "${origin}/${branch}*"]], /* Trailing '*' required to see bug */
                  browser: [$class: 'GithubWeb', repoUrl: "${repo}"],
                  extensions: [
                    [$class: 'CloneOption', honorRefspec: true, noTags: true],
                    [$class: 'WipeWorkspace'] /* WipeWorkspace causes the failure due to busy pack file */
                  ],
                  gitTool: implementation,
                  userRemoteConfigs: [[name: "${origin}", refspec: "+refs/heads/${branch}:refs/remotes/${origin}/${branch}", url: "${repo}"]]
                 ]
                )
        my_check.assertCondition(fileExists('.git/objects'), '.git/objects does not exist after checkout')
      }
      stage("Check ${gitImplementation}") {
        /* Call the ant build. */
        def my_step = new com.markwaite.Build()
        my_step.ant 'info'
        def my_check = new com.markwaite.Assert()
        my_check.logContains('.*user dir is .*', 'Ant output missing user dir report')
      }
    }
  }
}

parallel(tasks)
