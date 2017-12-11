#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 9 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '9']]])

def branch='JENKINS-38860'

/* The "type" parameter must be one of the string values reported
 * by build.xml, either 'git' or 'src'.
 */
@NonCPS
def assertSubmoduleCount(manager, String type) {
  def checkStep = new com.markwaite.Assert()
  java.util.regex.Matcher matcher = manager.getLogMatcher(".*submodule." + type + ".count=([0-9]+)")
  def message = "Expected submodule " + type + " dir count not found"
  if (matcher.matches()) {
      message = "Found " + matcher.group(1) + " submodule " + type + " dirs instead of 1"
  }
  checkStep.logContains(".*submodule." + type + ".count=1", message)
}

node('!CentOS-6') { // CentOS 6 git version too old for submodule support
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                   name: 'bugs-origin',
                                   refspec: "+refs/heads/${branch}:refs/remotes/bugs-origin/${branch}",
                                  ]],
              branches: [[name: branch]],
              browser: [$class: 'GithubWeb',
                        repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [[$class: 'AuthorInChangelog'],
                           [$class: 'CleanCheckout'],
                           [$class: 'CloneOption',
                             honorRefspec: true,
                             noTags: true,
                             timeout: 4],
                           [$class: 'LocalBranch', localBranch: branch],
                           [$class: 'PruneStaleBranch'],
                           [$class: 'WipeWorkspace'],
                           [$class: 'SubmoduleOption',
                             disableSubmodules: false,
                             parentCredentials: true,
                             recursiveSubmodules: true,
                             reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                             trackingSubmodules: false],
                           ],
              gitTool: 'Default', /* Submodule authentication not supported in JGit */
             ])
  }

  stage('Build') {
    /* Call the ant build. */
    def buildStep = new com.markwaite.Build()
    buildStep.ant "info"
  }

  stage('Verify') {
    def checkStep = new com.markwaite.Assert()

    /* Check that submodule README contains expected bug URL */
    checkStep.logContains(".*https://issues.jenkins-ci.org/browse/JENKINS-15103.*", "No submodule README output")

    /* Check exactly 1 submodule in tests-submodule directory */
    assertSubmoduleCount(manager, "src")

    /* Check exactly 1 submodule in .git/modules/tests-submodule directory */
    assertSubmoduleCount(manager, "git")

  }

}
