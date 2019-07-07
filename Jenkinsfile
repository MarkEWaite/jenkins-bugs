#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def checkout_result = {}
def expected_sha1 = "f0fae702de30331a8ce913cdb87ac0bdf990d85f"

node('testing-a-jagent') {
  stage('Checkout') {
    try {
      checkout_result = checkout([$class: 'GitSCM',
                  branches: [[name: 'v5.1.15']],
                  extensions: [
                    [$class: 'LocalBranch', localBranch: 'branch-v5.1.15'],
                    [$class: 'CloneOption', depth: 1, honorRefspec: true, noTags: false, reference: '/var/lib/git/mwaite/linux/linux-stable.git', shallow: true, timeout: 1]],
                  gitTool: scm.gitTool,
                  userRemoteConfigs: [[credentialsId: 'mwaite-git-markwaite-net-rsa-private-key-from-mark-pc2', url: 'mwaite@git.markwaite.net:git/bare/linux/linux-stable.git']]])
      expected_sha1 = checkout_result['GIT_COMMIT']
    } catch (Exception e) {
      echo "Caught exception: ${e}"
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
    my_check.logDoesNotContain(".*Caught exception: .*Exception.*", "Exception caught in checkout")
  }
}
