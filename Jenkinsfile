#!groovy

@Library(value='globalPipelineLibraryMarkEWaiteModernGitHub@v1.1', changelog=false) _
import com.markwaite.Assert
import com.markwaite.Build

def repo_url = scm.userRemoteConfigs[0].url

node('linux && !cloud') { // Needs curl installed, needs local access to Jenkins server
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: 'JENKINS-59016']],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'LocalBranch', localBranch: '**'],
                           [$class: 'CleanCheckout'], // ant info clutters workspace with output files
                          ],
              gitTool: scm.gitTool,
              userRemoteConfigs: [[refspec: '+refs/heads/JENKINS-59016:refs/remotes/origin/JENKINS-59016', url: repo_url ]],
            ])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-59016 reports branch scan does not use folder scoped credentials.  */
    my_check.logContains('.*reportScanLogResults script exited normally.*',  'branch scan test script unexpected exit')
    my_check.logContains('.*Branch scan log .* contains expected content.*', 'Branch scan not authenticated')
  }
}
