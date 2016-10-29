#!groovy

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node("git-1.8+ && !windows") { // Windows garbles Japanese commit text
  stage('Checkout') {
    checkout scm
  }

  stage('Build') {
    /* Call the ant build. */
    ant "increment"
  }

  stage('Verify') {
    // Does not check the actual bug report
    // Bug report was that recent changes are not in the recent changes list
    // This checks that the log writes Japanese, not that the recent changes do
    def repo = "${env.JENKINS_URL}/userContent.git"
    def check = fileLoader.fromGit('pipelineChecks', "${repo}", 'master', null, '')
    check.logContains(".*ビルド番号をインクリメント.*", "Missing localized text 1.")
    check.logContains(".*でした.*", "Missing localized text 2.")
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
