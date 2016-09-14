#!groovy

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
             strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch="JENKINS-33827"

node('master') {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                   name: 'jenkins-bugs-origin',
                                   refspec: "+refs/heads/${branch}:refs/remotes/jenkins-bugs-origin/${branch}",
                                  ]],
              branches: [[name: "*/${branch}"]],
              browser: [$class: 'GithubWeb',
                        repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
              extensions: [[$class: 'AuthorInChangelog'],
                           [$class: 'CheckoutOption', timeout: 1],
                           [$class: 'CleanCheckout'],
                           [$class: 'CloneOption',
                            depth: 3,
                            honorRefspec: true,
                            noTags: true,
                            reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                            shallow: true,
                            timeout: 3],
                           [$class: 'LocalBranch', localBranch: '**'],
                           [$class: 'PruneStaleBranch'],
                           [$class: 'SubmoduleOption',
                            disableSubmodules: false,
                            recursiveSubmodules: true,
                            reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                            trackingSubmodules: false],
                           [$class: 'WipeWorkspace'],
                           ],
             ])
  }

  stage('Build') {
    /* Call the ant build. */
    ant "-Dconfig.file=../config.xml count"
    // ant "count"
  }

  stage('Verify') {
    if (!manager.logContains(".* has 4 matching lines, 4 expected.*")) {
      manager.addWarningBadge("No matching line count.")
      manager.createSummary("warning.gif").appendText("<h1>No matching line count!</h1>", false, false, false, "red")
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
