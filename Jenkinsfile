#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout scm
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logDoesNotContain(".*  origin.*JENKINS-37727-.*pruned", "Too many JENKINS-37727-* branches!")
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
