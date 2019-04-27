#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout scm: [ $class: 'GitSCM',
                    branches: [[name: 'origin/JENKINS-51638']],
                    extensions: [[
                      $class: 'PreBuildMerge',
                      options: [
                        fastForwardMode: 'FF',
                        mergeRemote: 'origin',
                        mergeStrategy: 'default',
                        mergeTarget: 'JENKINS-51638-project-1'
                      ]
                    ]],
                    userRemoteConfigs: [[
                      /* Needs work here */
                      credentialsId: 'gitcredentialshere',
                      name: 'origin',
                      url: "https://somegit.somewhere"
                    ]]
                  ]

  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    if (currentBuild.number > 1) { // Don't check first build
      /* Not a good check of the bug, need better assertions */
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
