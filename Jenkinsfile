#!/usr/bin/env groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 7 most recent builds. */
/* Cancel prior builds if a new build starts. */
properties([buildDiscarder(logRotator(numToKeepStr: '7')),
            disableConcurrentBuilds(abortPrevious: true)
           ])

def branch='JENKINS-66885'
def origin='J-66885-origin'

node('!windows && !cloud') {
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
    String changesApiUrl = "${env.BUILD_URL}api/xml?wrapper=changes&xpath=//changeSet//comment"
    println "job URL is '${changesApiUrl}'"
    String changeDescription =
      new URL(changesApiUrl).getText(connectTimeout: 1000,
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
