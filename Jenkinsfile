#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

def branch = 'JENKINS-43198'
def origin = "${branch}-origin"
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
        checkout([$class: 'GitSCM',
                  branches: [[name: "${origin}/${branch}"]],
                  browser: [$class: 'GithubWeb', repoUrl: "${repo}"],
                  extensions: [
                    [$class: 'CloneOption', honorRefspec: true, noTags: true],
                    [$class: 'CleanBeforeCheckout'], /* This shows the bug */
                  ],
                  gitTool: "${gitImplementation}",
                  userRemoteConfigs: [[name: "${origin}", refspec: "+refs/heads/${branch}:refs/remotes/${origin}/${branch}", url: "${repo}"]]
                 ]
                )
        my_check.assertCondition(fileExists('.git/objects'), '.git/objects does not exist after checkout')
        my_check.assertCondition(!fileExists('this_is_ok/not_ok/more/subdirs/build.number'), 'not_ok exists after clean')
      }
      stage("Check ${gitImplementation}") {
        /* Call the ant build. */
        def my_step = new com.markwaite.Build()
        my_step.ant 'info'
        def my_check = new com.markwaite.Assert()
        my_check.logContains('.*user dir is .*', 'Ant output missing user dir report')
        my_check.assertCondition(fileExists('this_is_ok/not_ok/more/subdirs/build.number'), 'not_ok does not exist after build')
        /* New checkout with clean */
        checkout([$class: 'GitSCM',
                  branches: [[name: "${origin}/${branch}"]],
                  browser: [$class: 'GithubWeb', repoUrl: "${repo}"],
                  extensions: [
                    [$class: 'CloneOption', honorRefspec: true, noTags: true],
                    [$class: 'CleanBeforeCheckout'], /* This shows the bug */
                  ],
                  gitTool: "${gitImplementation}",
                  userRemoteConfigs: [[name: "${origin}", refspec: "+refs/heads/${branch}:refs/remotes/${origin}/${branch}", url: "${repo}"]]
                 ]
                )
        my_check.assertCondition(fileExists('.git/objects'), '.git/objects does not exist after checkout')
        my_check.assertCondition(!fileExists('this_is_ok/not_ok/more/subdirs/build.number'), 'not_ok exists after clean')
      }
    }
  }
}

parallel(tasks)
