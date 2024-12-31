#!groovy

// Fails
// @Library('globalPipelineLibraryMarkEWaiteModernGitHub@fb962c98d8eacc4e70a3b209db259e5c8a4294d8') _ // Checkout JENKINS-48061 SHA1 reference with modern GitHub SCM
// @Library('globalPipelineLibraryMarkEWaiteModernGitHub@9f52be42b165a42b20be4fcd03081af28bde3b93') _ // Checkout JENKINS-48061 older SHA1 reference with modern GitHub SCM
// @Library('globalPipelineLibraryMarkEWaiteModernGitHub@5093f89ac0057a471081ff5a5bf94c20f9acae97') _ // Checkout JENKINS-48061 older SHA1 reference with modern GitHub SCM

// Fails
// @Library('globalPipelineLibraryMarkEWaite@0c30065c158df07e55eeda283a7db3ff19bbfe01') _ // Checkout JENKINS-48061 SHA1 reference with legacy SCM
// @Library('globalPipelineLibraryMarkEWaite@5093f89ac0057a471081ff5a5bf94c20f9acae97') _ // Checkout JENKINS-48061 older SHA1 reference with legacy SCM

// Works
@Library('globalPipelineLibraryMarkEWaiteModernGitHub@v1.1.2') _ // A work around
// @Library('globalPipelineLibraryMarkEWaiteModernGitHub@branch-for-tag-v1.1') _ // A work around
// @Library('globalPipelineLibraryMarkEWaiteModern@0c30065c158df07e55eeda283a7db3ff19bbfe01') _ // Checkout JENKINS-48061 SHA1 reference with modern git SCM
// @Library('globalPipelineLibraryMarkEWaiteModern@5093f89ac0057a471081ff5a5bf94c20f9acae97') _ // Checkout JENKINS-48061 older SHA1 reference with modern git SCM

import com.markwaite.Assert
import com.markwaite.Build

import static groovy.json.JsonOutput.*

def branch = 'JENKINS-48061-legacy'
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
