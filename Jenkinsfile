#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node('git-1.9+') { // shallow clone fails on older git with 'git fetch-pack: expected shallow list'
  stage('Checkout') {
    /* reduce clone data volume with reference repo, shallow clone, no
       tags, and honor the refspec */
    checkout([$class: 'GitSCM',
              branches: [[name: 'JENKINS-41906']],
              browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [[$class: 'CloneOption',
                            honorRefspec: true,
                            noTags: true,
                            reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                            shallow: true,
                            depth: 1,
                           ]],
              userRemoteConfigs: [[name: 'JENKINS-41906-origin',
                                   refspec: '+refs/heads/JENKINS-41906:refs/remotes/JENKINS-41906-origin/JENKINS-41906 ',
                                   url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* Ignore this cause if build number == 1 */
    def ignoreThisCause = (currentBuild.number == 1)
    // for (cause in currentBuild.rawBuild.getCauses()) {
      // println "'${cause.shortDescription}' caused this build"
      // /* Ignore this cause build started by user */
      // if (cause.shortDescription.startsWith("Started by user")) {
        // currentBuild.description = cause.shortDescription
        // ignoreThisCause = true
      // }
    // }
    if (ignoreThisCause == true) {
      /* Build not expected to include a change */
      my_check.logDoesNotContain('.*Author:.*', "Unexpected commit in build ${currentBuild.number}")
    } else {
      /* Subsequent builds should be triggered by 1 or more changes */
      my_check.logContains('.*Author:.*', 'No recent commit, build should not run')
    }
  }
}
