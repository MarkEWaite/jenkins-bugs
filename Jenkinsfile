#!groovy

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout scm
  }

  stage('Build') {
    /* Call the ant build. */
    ant "info"
  }

  stage('Verify') {
    def latest_sha1 = getSHA1("refs/remotes/origin/features/JENKINS-37263^{commit}")
    def current_sha1 = getSHA1("HEAD")

    echo "Latest sha1 is ${latest_sha1}, current sha1 is ${current_sha1}"

    if (latest_sha1 != current_sha1) {
      manager.addWarningBadge("Missed latest: ${latest_sha1}, was ${current_sha1}.")
      manager.createSummary("warning.gif").appendText("<h1>Missed latest commit ${latest_sha1}, was ${current_sha1}!</h1>", false, false, false, "red")
      manager.buildUnstable()
    }
  }
}

def getSHA1(def commit) {
  if (isUnix()) {
    sh "git rev-parse ${commit} > .sha1"
    sha1 = readFile ".sha1"
    sh "rm .sha1"
  } else {
    // Windows treats caret as special character, must escape it
    if (commit.contains("^")) {
      commit = commit.replace("^", "^^")
    }
    bat "git rev-parse ${commit} > .sha1"
    sha1 = readFile ".sha1"
    bat "del .sha1"
  }
  return sha1.replaceAll("\\s", "")
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
