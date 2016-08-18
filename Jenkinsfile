#!groovy

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage 'Checkout'
  checkout scm

  stage 'Build'

  /* Call the ant build. */
  ant "info"

  stage 'Verify'

  def sha1 = "unknown"
  if (isUnix()) {
    sh "git rev-parse refs/remotes/origin/features/JENKINS-37263^{commit} > .sha1"
    sha1 = readFile ".sha1"
    sh "echo sha1 is ${sha1}"
    sh "cat .sha1"
    sh "rm .sha1"
  } else {
    bat "git rev-parse refs/remotes/origin/features/JENKINS-37263^{commit} > .sha1"
    sha1 = readFile ".sha1"
    bat "del .sha1"
  }

  if (!manager.logContains(".*> git checkout -b features/JENKINS-37263 ${sha1}")) {
    manager.addWarningBadge("Missed latest commit ${sha1}.")
    manager.createSummary("warning.gif").appendText("<h1>Missed latest commit ${sha1}!</h1>", false, false, false, "red")
    manager.buildUnstable()
  }
  if (!manager.logContains(".*[*] features/JENKINS-37263")) {
    manager.addWarningBadge("Missing branch name.")
    manager.createSummary("warning.gif").appendText("<h1>Missing branch name!</h1>", false, false, false, "red")
    manager.buildUnstable()
  }

}

/* Run ant from tool "ant-latest" */
void ant(def args) {
  /* Get jdk tool. */
  String jdktool = tool name: "jdk8", type: 'hudson.model.JDK'

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
