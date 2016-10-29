#!groovy

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node("git-1.8+ && !windows") { // Windows garbles Japanese commit text
  def step
  def check

  stage('Checkout') {
    checkout scm
    def repo = "${env.JENKINS_URL}/userContent.git"
    fileLoader.withGit("${repo}", 'master', null, '') {
      step = fileLoader.load('pipelineSteps');
      check = fileLoader.load('pipelineChecks');
    }
  }

  stage('Build') {
    /* Call the ant build. */
    step.ant "increment"
  }

  stage('Verify') {
    // Does not check the actual bug report
    // Bug report was that recent changes are not in the recent changes list
    // This checks that the log writes Japanese, not that the recent changes do
    check.logContains(".*ビルド番号をインクリメント.*", "Missing localized text 1.")
    check.logContains(".*でした.*", "Missing localized text 2.")
  }
}
