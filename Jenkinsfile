6#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-30515'
def non_existent_credentials_id = 'JENKINS-30515-non-existent-credentials-id'
def existing_but_unusable_credentials_id_1 = 'MarkE-ed25519-private-key-mark-pc4'
def existing_but_unusable_credentials_id_2 = 'jagent-aws-ubuntu-18-a-rsa-private-key-with-passphrase'

def public_repository_urls =  [
                                'https://github.com/MarkEWaite/jenkins-bugs',
                              ]

def private_repository_urls = [
                                'git@github.com:MarkEWaite/jenkins-bugs.git',
                                'https://github.com/MarkEWaite/jenkins-bugs-private',
                                'git@github.com:MarkEWaite/jenkins-bugs-private.git',
                              ]

def credential_ids  =         [
                                non_existent_credentials_id,
                                existing_but_unusable_credentials_id_1,
                                existing_but_unusable_credentials_id_2,
                              ]

node('home-hasphrase || mark-pc2-beemarkwaite || remote || jagent') {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                    refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"]]])

    for (repository_url in public_repository_urls) {
      for (credential_id in credential_ids) {
        ws() {
          checkout([$class: 'GitSCM',
                      branches: [[name: 'master']],
                      extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true]],
                      gitTool: scm.gitTool,
                      userRemoteConfigs: [[url: repository_url,
                                           credentialsId: credential_id,
                                           name: "${credential_id}-origin",
                                           refspec: "+refs/heads/master:refs/remotes/${credential_id}-origin/master",
                                          ]]])
        }
      }
    }

    def my_assert = new com.markwaite.Assert()
    for (repository_url in private_repository_urls) {
      for (credential_id in credential_ids) {
        ws() {
          try {
            checkout([$class: 'GitSCM',
                        branches: [[name: 'master']],
                        extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true]],
                        gitTool: scm.gitTool,
                        userRemoteConfigs: [[url: repository_url,
                                             credentialsId: credential_id,
                                             name: "X${credential_id}-originX",
                                             refspec: "+refs/heads/master:refs/remotes/X${credential_id}-originX/master",
                                            ]]])
            my_assert.assertCondition(false, "private checkout of ${repository_url} with ${credential_id} succeeded unexpectedly")
          } catch (Exception e) {
            echo "Caught exception was ${e}"
          }
        }
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
    for (credential_id in credential_ids) {
      my_check.logContains(".*(using credential|Warning: CredentialId).*${credential_id}.*", "credential ${credential_id} not reported")
    }
    my_check.logContains(".*user dir is .*", 'Missing user dir report')
  }
}
