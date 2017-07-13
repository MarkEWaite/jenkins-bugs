#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

def branch = 'JENKINS-45489'
def origin = "${branch}-original"
def repo = 'https://github.com/MarkEWaite/jenkins-bugs'

import java.util.Random

def random = new Random()

def implementations = [ 'git', 'jgit', 'jgitapache' ]

def tasks = [ : ]

def checkout_result = [ : ]

def first_checkout_result = ""

for (int i = 0; i < implementations.size(); ++i) {
  def gitImplementation = implementations[i]
  tasks[gitImplementation] = {
    node('windows') {
      stage("Checkout ${gitImplementation}") {
        def my_check = new com.markwaite.Assert()
        if (random.nextBoolean()) { /* Randomly use pipeline native command to wipe workspace */
          deleteDir()
        }
        def implementation = gitImplementation == "git" ? "Default" : gitImplementation
        checkout_result[implementation] = checkout([$class: 'GitSCM',
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
        if (first_checkout_result == "") {
            first_checkout_result = checkout_result[implementation]
        }
        def this_result = checkout_result[implementation]
        my_check.assertCondition(first_checkout_result == this_result, first_checkout_result + " != " + this_result)
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
