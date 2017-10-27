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

def first = ""

for (int i = 0; i < implementations.size(); ++i) {
  def gitImplementation = implementations[i]
  tasks[gitImplementation] = {
    node {
      stage("Checkout ${gitImplementation}") {
        def implementation = gitImplementation == "git" ? "Default" : gitImplementation
        checkout_result[implementation] = checkout([$class: 'GitSCM',
                  branches: [[name: "${origin}/${branch}*"]], /* Trailing '*' required to see bug */
                  browser: [$class: 'GithubWeb', repoUrl: repo],
                  extensions: [
                    [$class: 'CloneOption', honorRefspec: true, noTags: true],
                  ],
                  gitTool: implementation,
                  userRemoteConfigs: [[name: "${origin}", refspec: "+refs/heads/${branch}:refs/remotes/${origin}/${branch}", url: "${repo}"]]
                 ]
                )
        if (first == "") {
            first = checkout_result[implementation]
        }
        def latest = checkout_result[implementation]
        print latest
        def my_step = new com.markwaite.Build()
        def my_sha1 = my_step.getSHA1("HEAD")
        def my_check = new com.markwaite.Assert()
        my_check.assertCondition(first["GIT_COMMIT"] == latest["GIT_COMMIT"], first["GIT_COMMIT"] + " != " + latest["GIT_COMMIT"])
        my_check.assertCondition(first["GIT_COMMIT"] == my_sha1, first["GIT_COMMIT"] + " != " + my_sha1)
        my_check.assertCondition(first["GIT_COMMITTER_NAME"] == latest["GIT_COMMITTER_NAME"], first["GIT_COMMITTER_NAME"] + " != " + latest["GIT_COMMITTER_NAME"])
        my_check.assertCondition(first["GIT_COMMITTER_EMAIL"] == latest["GIT_COMMITTER_EMAIL"], first["GIT_COMMITTER_EMAIL"] + " != " + latest["GIT_COMMITTER_EMAIL"])
        my_check.assertCondition(first["GIT_AUTHOR_NAME"] == latest["GIT_AUTHOR_NAME"], first["GIT_AUTHOR_NAME"] + " != " + latest["GIT_AUTHOR_NAME"])
        my_check.assertCondition(first["GIT_AUTHOR_EMAIL"] == latest["GIT_AUTHOR_EMAIL"], first["GIT_AUTHOR_EMAIL"] + " != " + latest["GIT_AUTHOR_EMAIL"])
      }
      stage("Check ${gitImplementation}") {
        /* Call the ant build. */
        def my_step = new com.markwaite.Build()
        my_step.ant 'info'
        def my_check = new com.markwaite.Assert()
        my_check.logContains('.*user dir is .*', 'Ant output missing user dir report')
        print checkout_result
      }
    }
  }
}

parallel(tasks)
