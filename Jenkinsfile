#!groovy

// Fails
// @Library(value='globalPipelineLibraryMarkEWaiteModernGitHub@0c30065c158df07e55eeda283a7db3ff19bbfe01', changelog=false) _ // Checkout JENKINS-48061 SHA1 reference with modern GitHub SCM
// @Library(value='globalPipelineLibraryMarkEWaiteModernGitHub@5093f89ac0057a471081ff5a5bf94c20f9acae97', changelog=false) _ // Checkout JENKINS-48061 SHA1 reference with modern GitHub SCM

// Fails
// @Library(value='globalPipelineLibraryMarkEWaite@0c30065c158df07e55eeda283a7db3ff19bbfe01', changelog=false) _ // Checkout JENKINS-48061 SHA1 reference with legacy SCM
// @Library(value='globalPipelineLibraryMarkEWaite@5093f89ac0057a471081ff5a5bf94c20f9acae97', changelog=false) _ // Checkout JENKINS-48061 SHA1 reference with legacy SCM

// Works
@Library(value='globalPipelineLibraryMarkEWaiteModern@0c30065c158df07e55eeda283a7db3ff19bbfe01', changelog=false) _ // Checkout JENKINS-48061 SHA1 reference with modern git SCM

// Fails
// @Library(value='globalPipelineLibraryMarkEWaiteModern@5093f89ac0057a471081ff5a5bf94c20f9acae97', changelog=false) _ // Checkout JENKINS-48061 SHA1 reference with modern git SCM

import com.markwaite.Assert
import com.markwaite.Build

import static groovy.json.JsonOutput.*

def branch = 'JENKINS-45489'
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
        checkout_result[implementation] = checkout([$class: 'GitSCM',
                  branches: [[name: branch]],
                  browser: [$class: 'GithubWeb', repoUrl: repo],
                  extensions: [
                    [$class: 'CloneOption', honorRefspec: true, noTags: true],
                  ],
                  gitTool: implementation,
                  userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repo]]
                 ]
                )
        if (first == "") {
            first = checkout_result[implementation]
        }
        def latest = checkout_result[implementation]
        println prettyPrint(toJson(latest))
        def my_step = new com.markwaite.Build()
        def my_sha1 = my_step.getSHA1("HEAD")

        def env_vars = [
                        "GIT_AUTHOR_EMAIL",
                        "GIT_AUTHOR_NAME",
                        "GIT_BRANCH",
                        "GIT_COMMIT",
                        "GIT_COMMITTER_EMAIL",
                        "GIT_COMMITTER_NAME",
                        "GIT_URL",
                       ]

        def my_check = new com.markwaite.Assert()
        my_check.assertCondition(first["GIT_COMMIT"] == my_sha1, first["GIT_COMMIT"] + " != " + my_sha1)
        for ( env_var in env_vars ) {
          my_check.assertCondition(first[env_var] == latest[env_var], env_var + ": " + first[env_var] + " != " + latest[env_var])
        }

        /* Call the ant build. */
        my_step.ant 'info'
        my_check.logContains('.*user dir is .*', 'Ant output missing user dir report')

        def buggy_env_vars = [
                        "GIT_AUTHOR_EMAIL",
                        "GIT_AUTHOR_NAME",
                        "GIT_COMMITTER_EMAIL",
                        "GIT_COMMITTER_NAME",
                       ]
        for ( env_var in buggy_env_vars ) {
          my_check.logContains(".*${env_var}=${latest[env_var]}", "$env_var mismatch")
        }
      }
    }
  }
}

parallel(tasks)
