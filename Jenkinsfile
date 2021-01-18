#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Poll every 13 minutes. */
properties([
            pipelineTriggers([pollSCM('H/13 * * * *')]),
            [$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '10']]
           ])

def changes

node('!cloud') { // Needs access to the Jenkins controller URL for curl
  stage('Checkout') {
    /* More complex checkout command seems to stop continuous false detection of changes */
    checkout([$class: 'GitSCM',
              branches: [[name: 'JENKINS-64656']],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'LocalBranch', localBranch: '**'],
                           [$class: 'CleanCheckout'],
                           [$class: 'AuthorInChangelog']
                          ],
              userRemoteConfigs: [[refspec: '+refs/heads/JENKINS-64656:refs/remotes/origin/JENKINS-64656',
                                   url: scm.userRemoteConfigs[0].url]],
            ])
    changes = changelogEntries(changeSets: currentBuild.changeSets)
  }

  stage('Build') {
    withEnv(["CHANGESET_SIZE=${changes.size()}"]) {
      /* Call the ant build. */
      def my_step = new com.markwaite.Build()
      my_step.ant 'info'
    }
  }

  stage('Verify') {
    /* JENKINS-64656 reports that message exclusion was ignored in Freestyle jobs.  */
    /* Since this is not a Freestyle job, assert that changeset is not empty. */
    if (currentBuild.number > 1 && changes.size() > 0) { // Only check builds with changes
      def my_check = new com.markwaite.Assert()
      my_check.logContains('.*Author:.*', 'Build started without a commit - no author line')
      my_check.logContains('.*Date:.*', 'Build started without a commit - no date line')
    }
  }
}
