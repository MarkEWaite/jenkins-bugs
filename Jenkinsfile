#!/usr/bin/env groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch='JENKINS-36637-jgit'
def origin='J-36637-origin'

node('master') {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                   name: origin,
                                   refspec: "+refs/heads/${branch}:refs/remotes/${origin}/${branch}",
                                  ]],
              branches: [[name: "${origin}/${branch}"]],
              extensions: [[$class: 'CloneOption',
                            honorRefspec: true,
                            noTags: true,
                            reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                            timeout: 3],
                           [$class: 'LocalBranch', localBranch: branch],
                           [$class: 'PruneStaleBranch'],
                          ],
              gitTool: 'jgit',
             ])
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    String jobName = env.JOB_NAME
    String jobPath = "job/" + jobName.replace("/", "/job/")
    String buildNumber = "${currentBuild.number}"
    String jobURL = "http://localhost:8080/${jobPath}/${buildNumber}/api/xml?wrapper=changes&xpath=//changeSet//comment"
    println "job URL is '${jobURL}'"
    String changeDescription =
      new URL(jobURL).getText(connectTimeout: 1000,
			      readTimeout: 5000,
			      useCaches: false,
			      allowUserInteraction: false,
			      requestProperties: ['Connection': 'close'])
    println "Change description is '" + changeDescription + "'"
    if (changeDescription.contains("<changes/>") ||
	!changeDescription.contains("<changes>") ||
	countSubstrings(changeDescription, "<comment>") < 2) { // Always expect at least 2 changes
      if (currentBuild.number > 1) { // Don't check first build
        manager.addWarningBadge("Missing recent changes output")
        manager.createSummary("warning.gif").appendText("<h1>Missing recent changes!</h1>", false, false, false, "red")
        manager.buildUnstable()
      }
    }
  }
}

int countSubstrings(String str, String subStr) {
  return (str.length() - str.replace(subStr, "").length()) / subStr.length();
}
