#!groovy

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node("master") { // relies on master node using default JENKINS_HOME value
  stage('Checkout') {
    sh "echo Working directory is `pwd`"
    sh "pwd"
    // checkout scm
  }

  stage('Verify') {
    if (!manager.logContains(".*Working directory is /var/jenkins_home/.*")) {
      manager.addWarningBadge("Missing expected working directory text.")
      manager.createSummary("warning.gif").appendText("<h1>Missing expected working directory text.</h1>", false, false, false, "red")
      manager.buildUnstable()
    }
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
