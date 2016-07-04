#!groovy

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node("!windows") { // Not Windows, my Windows machines garble Japanese commit message
  stage 'Checkout'
  checkout scm

  stage 'Build'
  /* Call the ant build. */
  ant "increment"

  stage 'Verify'
  // Does not check the actual bug report
  // Bug report was that recent changes are not in the recent changes list
  // This checks that the log writes Japanese, not that the recent changes do
  if (!manager.logContains(".*ビルド番号をインクリメント.*") ||
      !manager.logContains(".*でした.*")) {
    manager.addWarningBadge("Missing localized text.")
    manager.createSummary("warning.gif").appendText("<h1>Missing localized text!</h1>", false, false, false, "red")
    manager.buildUnstable()
  }
  println("Manager methods are")
  println(manager.metaClass.methods)
  println("Manager build methods are")
  println(manager.build.metaClass.methods)
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
