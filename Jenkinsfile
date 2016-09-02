#!groovy

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node("master") {
  stage('Checkout') {
    sh 'echo JENKINS_HOME is $JENKINS_HOME' 
    sh 'echo Working directory is `pwd`'
    sh 'pwd'
    // checkout scm
  }

  stage('Verify') {
    if (!manager.logContains(".*Working directory is ${env.JENKINS_HOME}.*") ||
        !manager.logContains("^Working directory is ${env.JENKINS_HOME}$")) {
      manager.addWarningBadge("Missing expected working directory text.")
      manager.createSummary("warning.gif").appendText("<h1>Missing expected working directory text.</h1>", false, false, false, "red")
      manager.buildUnstable()
    }
  }
}
