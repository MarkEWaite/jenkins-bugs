#!groovy

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch="JENKINS-22547"

node {
  stage 'Checkout'
  checkout([$class: 'GitSCM',
            userRemoteConfigs: [[name: 'bugs-origin',
                                 refspec: '+refs/heads/${branch}:refs/remotes/bugs-origin/${branch}',
                                 url: 'https://github.com/MarkEWaite/jenkins-bugs']]
            branches: [[name: '*/${branch}']],
            browser: [$class: 'GithubWeb',
                      repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
            extensions: [[$class: 'CheckoutOption', timeout: 37],
                         [$class: 'CleanBeforeCheckout'],
                         [$class: 'LocalBranch', localBranch: '**'],
                         [$class: 'PruneStaleBranch'],
                         [$class: 'AuthorInChangelog'],
                         [$class: 'CloneOption',
                          depth: 3,
                          honorRefspec: true,
                          noTags: true,
                          reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                          shallow: true,
                          timeout: 3]],
           ])

  stage 'Build'

  /* Call the ant build. */
  ant "info"

  stage 'Verify'
  if (!manager.logContains(".*git.*fetch.*timeout=3")) {
    manager.addWarningBadge("Missing clone timeout.")
    manager.createSummary("warning.gif").appendText("<h1>Missing clone timeout!</h1>", false, false, false, "red")
    manager.buildUnstable()
  }
  if (!manager.logContains(".*git.*checkout.*timeout=37")) {
    manager.addWarningBadge("Missing checkout timeout.")
    manager.createSummary("warning.gif").appendText("<h1>Missing checkout timeout!</h1>", false, false, false, "red")
    manager.buildUnstable()
  }
  if (!manager.logContains(".*[*] ${branch}")) {
    manager.addWarningBadge("Missing local branch checkout to ${branch}.")
    manager.createSummary("warning.gif").appendText("<h1>Missing local branch checkout to ${branch}!</h1>", false, false, false, "red")
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
