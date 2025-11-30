#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-76158'

def userRemoteConfigsIn = scm.userRemoteConfigs

def userRemoteConfigsIn_url           = scm.userRemoteConfigs[0].url
def userRemoteConfigsIn_name          = scm.userRemoteConfigs[0].name
def userRemoteConfigsIn_refspec       = scm.userRemoteConfigs[0].refspec
def userRemoteConfigsIn_credentialsId = scm.userRemoteConfigs[0].credentialsId

echo "Read userRemoteConfig[ url: $userRemoteConfigsIn_url, name: $userRemoteConfigsIn_name, refspec: $userRemoteConfigsIn_refspec, credentialsId: $userRemoteConfigsIn_credentialsId ]"

def branchesIn = scm.branches

def branchesIn_name = scm.branches[0].name

def gitToolIn = scm.gitTool

def extensionsIn = scm.extensions

// Needs more work to confirm extension properties are correctly whitelisted
echo "extensionsIn is $extensionsIn"
for (extension in extensionsIn) {
    echo "extension is ${extension}"
}

node {
  stage('Checkout') {
    checkout scmGit(
              // userRemoteConfigs: userRemoteConfigsIn,
              // Use branch name in refspec for speed
              userRemoteConfigs: [[ url: userRemoteConfigsIn_url,
                                    refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"
                                 ]],
              branches: branchesIn,
              // Use honor refspec for speed
              // Use local branch because it is easier to push from a branch
              extensions: extensionsIn + [cloneOption(honorRefspec: true, noTags: false), localBranch()],
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
    NS-my_check.logContains(".*.exec. JENKINS-76158-.*", 'No tags reported') }
}
