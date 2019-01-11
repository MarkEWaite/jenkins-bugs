#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-30515'
def non_existent_credentials_id = 'JENKINS-30515-non-existent-credentials-id'
def existing_but_unusable_credentials_id = 'MarkE-ed25519-private-key-mark-pc4'

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
                                existing_but_unusable_credentials_id,
                              ]

node('windows || home-hasphrase || remote') {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: branch]
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                    refspec: '+refs/heads/master:refs/remotes/origin/master']]])

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
	  echo "********** Before checkout ${repository_url} with ${credential_id}"
	  try {
	    checkout([$class: 'GitSCM',
			branches: [[name: 'master']],
			extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true]],
			gitTool: scm.gitTool,
			userRemoteConfigs: [[url: repository_url,
					     credentialsId: credential_id,
					     name: "${credential_id}-origin",
					     refspec: "+refs/heads/master:refs/remotes/${credential_id}-origin/master",
					    ]]])
	    my_assert.assertCondition(false, "private checkout of ${repository_url} with ${credential_id} succeeded unexpectedly")
	  } catch (Exception e) {
	    echo "********** Start of caught exception"
	    echo "Caught exception was ${e}"
	    echo "********** End of caught exception"
	  }
	  echo "********** After checkout ${repository_url} with ${credential_id}"
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
    for (repository_url in public_repository_urls) {
      for (credential_id in credential_ids) {
	my_check.logContains(".*[^/]${credential_id}[^-].*", "credential ${credential_id} not reported")
      }
    }
    for (repository_url in private_repository_urls) {
      for (credential_id in credential_ids) {
	my_check.logContains(".*Warning:.*[^/]${credential_id}[^-].*", "credential ${credential_id} not reported")
      }
    }
    my_check.logContains(".*user dir is .*", 'Missing expected output')
  }
}
