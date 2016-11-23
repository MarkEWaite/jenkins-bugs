#!groovy

// Jenkinsfile based check not feasible, since this requires an interactive
// check that the changes link is correct

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch="JENKINS-39905"

node {

  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: 'https://bitbucket.org/markewaite/jenkins-bugs.git',
                                   name: 'origin',
                                   refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}",
                                  ]],
              branches: [[name: "origin/${branch}"]],
              browser: [$class: 'GithubWeb',
                        repoUrl: 'https://bitbucket.org/markewaite/jenkins-bugs.git'],
              extensions: [[$class: 'AuthorInChangelog'],
                           [$class: 'CheckoutOption', timeout: 1],
                           [$class: 'CleanCheckout'],
                           [$class: 'CloneOption',
                            honorRefspec: true,
                            noTags: true,
                            reference: '/var/lib/git/mwaite/jenkins/jenkins-bugs.git',
                            timeout: 3],
                           [$class: 'LocalBranch', localBranch: "${branch}"],
                           [$class: 'PruneStaleBranch'],
                           ],
             ])
  }

  stage('Build') {
    /* Call the maven build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logContains(".*BUILD SUCCESS", "Build success message not found")
  }

}
