#!groovy

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch="JENKINS-20941-https-simple"
def repoUrl = scm.userRemoteConfigs[0].url

node('git-2.30+') { // Shallow clone requires git 1.9 or newer
  stage('Checkout') {
    deleteDir()
    checkout scmGit(
              userRemoteConfigs: [[url: repoUrl,
                                   name: 'jenkins-bugs-origin',
                                   refspec: "+refs/heads/${branch}:refs/remotes/jenkins-bugs-origin/${branch}",
                                  ]],
              branches: [[name: branch]],
              extensions: [authorInChangelog(),
                           checkoutOption(1),
                           cleanBeforeCheckout(),
                           cloneOption(
                            depth: 3,
                            honorRefspec: true,
                            noTags: true,
                            reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                            shallow: true,
                            timeout: 3),
                           localBranch(),
                           pruneStaleBranch(),
                           pruneTags(true),
                           submodule(
                            recursiveSubmodules: true,
                            reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                            threads: 2),
                           ],
              gitTool: 'Default',
             )
  }

  stage('Build') {
    /* Call the ant build. */
    ant "info"
  }

  stage('Verify') {
    if (!manager.logContains(".* JENKINS-20941 base branch")) {
      manager.addWarningBadge("No base branch comment.")
      createSummary('symbol-rocket plugin-ionicons-api').appendText("### No base branch comment!", false)
      manager.buildUnstable()
    }
  }

}

/* Run ant from tool "ant-latest" */
void ant(def args) {
  /* Get jdk tool. */
  String jdktool = tool name: 'jdk21', type: 'hudson.model.JDK'

  /* Get the ant tool. */
  def antHome = tool name: 'ant-latest', type: 'hudson.tasks.Ant$AntInstallation'

  /* Set JAVA_HOME, and special PATH variables. */
  List javaEnv = [
    "PATH+JDK=${jdktool}/bin", "JAVA_HOME=${jdktool}", "ANT_HOME=${antHome}",
  ]

  /* Call ant tool with java envVars. */
  withEnv(javaEnv) {
    if (isUnix()) {
      sh "${antHome}/bin/ant ${args}"
    } else {
      bat "${antHome}\\bin\\ant ${args}"
    }
  }
}
