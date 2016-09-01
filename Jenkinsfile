#!groovy

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

stage 'Checkout'
node("git-1.8+ && !windows") { // Windows garbles Japanese commit text
  checkout scm
}

stage 'Build'
node("!windows") { // Windows garbles Japanese commit text
  /* Call the ant build. */
  ant "increment"
}

stage 'Verify'
node("!windows") { // Windows garbles Japanese commit text
  // Does not check the actual bug report
  // Bug report was that recent changes are not in the recent changes list
  // This checks that the log writes Japanese, not that the recent changes do
  if (!manager.logContains(".*ビルド番号をインクリメント.*") ||
      !manager.logContains(".*でした.*")) {
    manager.addWarningBadge("Missing localized text.")
    manager.createSummary("warning.gif").appendText("<h1>Missing localized text!</h1>", false, false, false, "red")
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
