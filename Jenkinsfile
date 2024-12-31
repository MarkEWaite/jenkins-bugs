#!groovy

// Fails
@Library('globalPipelineLibraryMarkEWaiteModernGitHub@0c30065c158df07e55eeda283a7db3ff19bbfe01') _ // Checkout JENKINS-48061 SHA1 reference with modern GitHub SCM
// @Library('globalPipelineLibraryMarkEWaiteModernGitHub@v1.1.1') _ // Checkout JENKINS-48061 tag reference with modern GitHub SCM
// @Library('globalPipelineLibraryMarkEWaiteModernGitHub@branch-for-tag-v1.1') _ // A work around

// Fails
// @Library('globalPipelineLibraryMarkEWaite@0c30065c158df07e55eeda283a7db3ff19bbfe01') _ // Checkout JENKINS-48061 SHA1 reference with legacy SCM

// Works
// @Library('globalPipelineLibraryMarkEWaiteModern@0c30065c158df07e55eeda283a7db3ff19bbfe01') _ // Checkout JENKINS-48061 SHA1 reference with modern git SCM

import com.markwaite.Assert
import com.markwaite.Build

import static groovy.json.JsonOutput.*

def branch = 'JENKINS-48061-modern-github'
def repo = 'https://github.com/MarkEWaite/jenkins-bugs'

import java.util.Random
def random = new Random()

def implementations = [ 'git', 'jgit', 'jgitapache' ]
/* Randomize order of implementations */
Collections.shuffle(implementations, random)

def tasks = [ : ]

def checkout_result = [ : ]

def first = ""

for (int i = 0; i < implementations.size(); ++i) {
  def gitImplementation = implementations[i]
  tasks[gitImplementation] = {
    node {
      stage("Check ${gitImplementation}") {
        def implementation = gitImplementation == "git" ? "Default" : gitImplementation
        checkout([$class: 'GitSCM',
                  branches: [[name: branch]],
                  browser: [$class: 'GithubWeb', repoUrl: repo],
                  extensions: [
                    [$class: 'CloneOption', honorRefspec: true, noTags: true],
                  ],
                  gitTool: implementation,
                  userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repo]]
                 ]
                )

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
