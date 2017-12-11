#!groovy

@Library(value='globalPipelineLibraryMarkEWaiteModernGitHub@v1.1', changelog=false) _
import com.markwaite.Assert
import com.markwaite.Build

node('linux') { // Needs curl installed
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: 'JENKINS-34350']],
              browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'LocalBranch', localBranch: '**'],
                           [$class: 'CleanCheckout'],
                           [$class: 'AuthorInChangelog']
                          ],
              userRemoteConfigs: [[name: 'bugs-origin',
                                   refspec: '+refs/heads/JENKINS-34350:refs/remotes/bugs-origin/JENKINS-34350',
                                   url: 'https://github.com/MarkEWaite/jenkins-bugs']],
            ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-34350 reports that notifyCommit breaks when CSRF protection is enabled.  */
    my_check.logContains('.*notifyCommit script exited normally.*', 'notifyCommit script output missing')
  }
}
