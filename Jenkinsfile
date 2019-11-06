#!groovy

@Library(value='globalPipelineLibraryMarkEWaiteModernGitHub@v1.1', changelog=false) _
import com.markwaite.Assert
import com.markwaite.Build

// Narrow the respec to only this branch
def branch = 'JENKINS-59016'
def myRemoteConfigs = scm.userRemoteConfigs
myRemoteConfigs[0].refspec = myRemoteConfigs[0].refspec.replace('*', branch)

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node('linux && !cloud') { // Needs curl installed, needs local access to Jenkins server
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'LocalBranch', localBranch: '**'],
                           [$class: 'CleanCheckout'], // ant info clutters workspace with output files
                          ],
              gitTool: scm.gitTool,
              userRemoteConfigs: myRemoteConfigs
            ])
    println("Change sets is ${currentBuild.changeSets}")
    println("Change set[0] is ${currentBuild.changeSets[0]}")
    println("Change set[0] items are ${currentBuild.changeSets[0].items}")
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
