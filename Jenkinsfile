#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-49757'

node('!cloud && !windows') { // Must have http access to master and must have sh step available
  stage('Checkout') {
    deleteDir() // Must wipe directory to see duplicate fetch
    checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                            ],
                gitTool: 'git', // Test only applies to command line git
                userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: scm.userRemoteConfigs[0].url]]
            ])
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
    def my_check = new com.markwaite.Assert()
    my_check.logContains(".*Count of git fetch on agent: 1.*", "Wrong git fetch count in scripted pipeline")
  }
}
