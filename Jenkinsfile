#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-75288'

def userRemoteConfigsIn = scm.userRemoteConfigs

def userRemoteConfigsIn_url           = scm.userRemoteConfigs[0].url
def userRemoteConfigsIn_name          = scm.userRemoteConfigs[0].name
def userRemoteConfigsIn_refspec       = scm.userRemoteConfigs[0].refspec
def userRemoteConfigsIn_credentialsId = scm.userRemoteConfigs[0].credentialsId

echo "Read userRemoteConfig[ url: $userRemoteConfigsIn_url, name: $userRemoteConfigsIn_name, refspec: $userRemoteConfigsIn_refspec, credentialsId: $userRemoteConfigsIn_credentialsId ]"

def branchesIn = scm.branches

def branchesIn_name = scm.branches[0].name

def doGenerateSubmoduleConfigurationsIn = scm.doGenerateSubmoduleConfigurations // untested with 'true' value, no known uses

def submoduleCfgIn = scm.submoduleCfg

if (submoduleCfgIn) {
    def submoduleCfgIn_submoduleName = scm.submoduleCfg[0]?.submoduleName
    def submoduleCfgIn_branches0     = scm.submoduleCfg[0]?.branches[0]
} else {
    echo 'No scm.submoduleCfg, did not read properties'
}

def gitToolIn = scm.gitTool

def extensionsIn = scm.extensions

// Needs more work to confirm extension properties are correctly whitelisted
echo "extensionsIn is $extensionsIn"
for (extension in extensionsIn) {
    echo "extension is ${extension}"
}

// JENKINS-75288 - RejectedAccessException despite method being whitelisted
// Needs more work to read nested choice of objects assigned to browser
// Needs to be whitelisted on the hudson.scm.SCM object, not the git plugin
def browserIn = scm.browser

node {
  stage('Checkout') {
    checkout scmGit(
              // userRemoteConfigs: userRemoteConfigsIn,
              // Use branch name in refspec for speed
              userRemoteConfigs: [[ url: userRemoteConfigsIn_url,
                                    refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"
                                 ]],
              branches: branchesIn,
              browser: browserIn,
              // Use shallow clone and honor refspec for speed, this branch does not need full depth clone
              extensions: extensionsIn + [cloneOption(honorRefspec: true, noTags: true, shallow: true, depth: 1), localBranch()],
              gitTool: scm.gitTool
    )
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*[*] ${branch}.*", 'Wrong branch reported')
  }
}
