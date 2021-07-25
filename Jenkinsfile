#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch='JENKINS-29977'
def repo_url=scm.userRemoteConfigs[0].url

node('linux && git-1.8+ && !cloud') { // ant command calls shell script that calls curl that connects to Jenkins server
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: repo_url,
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
    if (currentBuild.number > 1 && currentBuild.changeSets.size() > 0) { // Don't check first build or builds with no changes
      my_check.logDoesNotContain('.*First time build.*Skipping changelog.*', 'Later build incorrectly a first time build') // JENKINS-60159
      // Check for start of message in HTML output
      my_check.logContains('.*User interface truncates change messages.*', 'Start of commit message missing')
      // Check for middle of message in HTML output
      my_check.logContains('.*with an intentionally long first line of the commit message.*', 'Tail of commit message missing')
    }
  }
}
