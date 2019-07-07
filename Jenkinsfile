#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch = 'JENKINS-58349'

def checkout_result = {}
def expected_sha1 = "f0fae702de30331a8ce913cdb87ac0bdf990d85f"

/* Only run on local agents, not cloud agents.  Cloud agents can't see my local git caching server */
node('(testing-a-jagent || debian9-a-mwaite) && !cloud') {
  deleteDir() // Force each run to be a fresh copy
  checkout([$class: 'GitSCM',
          branches: [[name: branch]],
          extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                       [$class: 'LocalBranch', localBranch: branch]
                      ],
          gitTool: scm.gitTool,
          userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])

  /* Checkout linux-stable tag v5.1.15 into a subdirectory with local branch name branch-v5.1.15 */
  /* Clone from local git server instead of internet git server */
  /* Set timeout to 1 minute to increase chances of hitting the timeout */
  dir('linux-stable') {
    stage('Checkout') {
      try {
        checkout_result = checkout([$class: 'GitSCM',
                    branches: [[name: 'v5.1.15']],
                    extensions: [
                      [$class: 'LocalBranch', localBranch: 'branch-v5.1.15'],
                      [$class: 'CloneOption', reference: '/var/lib/git/mwaite/linux/linux-stable.git', timeout: 1]],
                    gitTool: scm.gitTool,
                    userRemoteConfigs: [[credentialsId: 'mwaite-git-markwaite-net-rsa-private-key-from-mark-pc2', url: 'mwaite@git.markwaite.net:git/bare/linux/linux-stable.git']]])
        expected_sha1 = checkout_result['GIT_COMMIT']
      } catch (Exception e) {
        echo "Caught linux-stable checkout exception: ${e}"
      }
    }
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*[*] branch-v5.1.15.*", "Wrong branch reported, expected 'branch-v5.1.15'")
    my_check.logContains(".*JENKINS-58349-HEAD-commit-SHA1-is-${expected_sha1}.*", "Wrong sha1 checkout at HEAD, expected '${expected_sha1}'")
    my_check.logContains(".*git fetch .*--force --progress.* # timeout=1[^0-9]*", "Missing timeout=1")
    my_check.logDoesNotContain(".*Caught linux-stable checkout exception: .*Exception.*", "Exception caught in checkout")
  }
}
