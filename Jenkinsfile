#!groovy

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch="JENKINS-36507"

node {
  stage 'Checkout'
  checkout([$class: 'GitSCM',
            userRemoteConfigs: [[name: 'bugs-origin',
                                 refspec: "+refs/heads/${branch}:refs/remotes/bugs-origin/${branch}",
                                 url: 'https://github.com/MarkEWaite/jenkins-bugs']],
            branches: [[name: "*/${branch}"]],
            browser: [$class: 'GithubWeb',
                      repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
            extensions: [[$class: 'AuthorInChangelog'],
                         [$class: 'CheckoutOption', timeout: 37],
                         [$class: 'CleanBeforeCheckout'],
                         [$class: 'CloneOption',
                          depth: 3,
                          honorRefspec: true,
                          noTags: true,
                          reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                          shallow: true,
                          timeout: 3],
                         [$class: 'LocalBranch', localBranch: '**'],
                         [$class: 'PruneStaleBranch'],
                        ]
           ])

  stage 'Build'

  /* Call the ant build. */
  ant "info"

  stage 'Verify'
  if (!manager.logContains(".*[*] JENKINS-36507")) {
    manager.addWarningBadge("Missing current branch name.")
    manager.createSummary("warning.gif").appendText("<h1>Missing current branch name!</h1>", false, false, false, "red")
    manager.buildUnstable()
  }
  if ( manager.logContains(".*JENKINS-22547")) {
    manager.addWarningBadge("Found extra branch name JENKINS-22547.")
    manager.createSummary("warning.gif").appendText("<h1>Found extra branch name JENKINS-22547!</h1>", false, false, false, "red")
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
