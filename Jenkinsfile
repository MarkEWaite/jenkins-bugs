#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 17 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '17']]])

def repo_url=scm.userRemoteConfigs[0].url
def branch_name='has-slash/JENKINS-29603'

def changes

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: branch_name]],
              extensions: [ [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                            // Ugh, a bug
                            // Avoid skipping commits by using a local branch named origin/has-slash/JENKINS-29603
                            // Local branch named has-slash/JENKINS-29603 misses commits in some cases
                            // Ugh, a bug
                            [$class: 'LocalBranch', localBranch: "origin/${branch_name}"]],
              gitTool: scm.gitTool,
              userRemoteConfigs: [[refspec: "+refs/heads/${branch_name}:refs/remotes/origin/${branch_name}", url: repo_url]]])
    changes = changelogEntries(changeSets: currentBuild.changeSets)
  }

  stage('Build') {
    withEnv(["CHANGESET_SIZE=${changes.size()}"]) {
      /* Call the ant build. */
      def my_step = new com.markwaite.Build()
      my_step.ant 'info'
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-29603 reports that notifYCommit with slash in branch name is ignored.  */
    if (currentBuild.number > 1 && changes.size() > 0) { // Don't check first build or if build has no changes
      my_check.logDoesNotContain('.*First time build.*Skipping changelog.*', 'Later build incorrectly a first time build') // JENKINS-60159
      my_check.logContains('.*.JENKINS-29603. build[+][+], was [1-9]+[0-9]*.*', 'No recent commit')
    }
    /* JENKINS-37044 reports that wrong working directory is used */
    my_check.logContains('.*working directory is .*has-slash.*JENKINS-29603.*.*', 'Wrong working directory')
  }
}
