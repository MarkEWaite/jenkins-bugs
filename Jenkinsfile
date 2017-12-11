#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

def repo_url='https://github.com/MarkEWaite/jenkins-bugs'
def branch='JENKINS-43818'

properties([parameters([string(defaultValue: "${branch}", description: 'Branch to build', name: 'BRANCH_SPECIFIER')])])

// properties([parameters([choice(choices: ["${branch}", 'master'], description: 'Branch to build (as a choice parameter)', name: 'BRANCH_SPECIFIER')])])

node {
  stage('Checkout') {
    branch = "${params.BRANCH_SPECIFIER}"
    echo "Branch specifier is ${branch}"
    checkout([$class: 'GitSCM',
              branches: [[name: "${branch}"]],
              doGenerateSubmoduleConfigurations: false,
              submoduleCfg: [],
              userRemoteConfigs: [[name: 'origin',
                                  refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}",
                                  url: "${repo_url}"]],
              extensions: [
                            [$class: 'CloneOption',
                              honorRefspec: true,
                              noTags: true,
                              reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                            [$class: 'LocalBranch', localBranch: "**"],
                          ],
            ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-43818 reports that parameters are ignored in branch specifier.  */
    if (currentBuild.number > 1) { // Don't check first build
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
    my_check.logContains('.*[*] JENKINS-43818.*', 'Expected branch name not in output')
  }
}
