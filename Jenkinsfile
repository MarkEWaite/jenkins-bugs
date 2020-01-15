#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-42860'

def userRemoteConfigsIn = scm.userRemoteConfigs

def userRemoteConfigsIn_url           = scm.userRemoteConfigs[0].url
def userRemoteConfigsIn_name          = scm.userRemoteConfigs[0].name
def userRemoteConfigsIn_refspec       = scm.userRemoteConfigs[0].refspec
def userRemoteConfigsIn_credentialsId = scm.userRemoteConfigs[0].credentialsId

def branchesIn = scm.branches

def branchesIn_name = scm.branches[0].name

def doGenerateSubmoduleConfigurationsIn = scm.doGenerateSubmoduleConfigurations

def submoduleCfgIn = scm.submoduleCfg

if (submoduleCfgIn) {
    def submoduleCfgIn_submoduleName = scm.submoduleCfg[0]?.submoduleName
    def submoduleCfgIn_branches0     = scm.submoduleCfg[0]?.branches[0]
} else {
    echo 'No scm.submoduleCfg, did not read properties'
}

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: userRemoteConfigsIn,
              branches: branchesIn,
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'LocalBranch', localBranch: branch]
                          ],
              gitTool: scm.gitTool,
            ]
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
