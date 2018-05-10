#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch='JENKINS-29977'

node('linux') { // ant command calls shell script that calls curl
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                   refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}",
                                  ]],
              branches: [[name: branch]],
              extensions: [[$class: 'AuthorInChangelog'],
                           [$class: 'CleanCheckout'],
                           [$class: 'CloneOption',
                            honorRefspec: true,
                            noTags: true,
                            reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                            timeout: 2],
                           [$class: 'LocalBranch', localBranch: branch],
                           ],
              gitTool: scm.gitTool
             ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    if (currentBuild.number > 1) { // Don't check first build
      // Check for start of message in HTML output
      my_check.logContains('.*<li>User interface truncates change messages.*</li>.*', 'Start of commit message missing')
      // Check for middle of message in HTML output
      my_check.logContains('.*<li>.*with an intentionally long first line of the commit message.*</li>.*', 'Tail of commit message missing')
    }
  }
}
