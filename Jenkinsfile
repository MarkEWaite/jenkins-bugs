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
    count = 0
    for (String logLine : currentBuild.rawBuild.getLog(100)) {
      if (logLine.contains("  origin") && logLine.contains("JENKINS-37727-") && !logLine.contains("pruned")) {
	count++
      }
    }

    if(count > 1) {
      manager.addWarningBadge("Too many JENKINS-37727-* branches.")
      manager.createSummary("warning.gif").appendText("<h1>Too many JENKINS-37727-* branches!</h1>", false, false, false, "red")
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
