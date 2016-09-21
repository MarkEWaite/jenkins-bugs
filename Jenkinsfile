#!/usr/bin/env groovy

/* Only keep the 7 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

def branch="JENKINS-28529"
def origin="J-28529-origin"

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                   name: "${origin}",
                                   refspec: "+refs/heads/${branch}:refs/remotes/${origin}/${branch}",
                                  ]],
              branches: [[name: "${origin}/${branch}"]],
              extensions: [[$class: 'CloneOption',
                            honorRefspec: true,
                            noTags: true,
                            reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                            timeout: 3],
                           [$class: 'LocalBranch', localBranch: '${branch}'],
                           [$class: 'PruneStaleBranch'],
                          ],
             ])
  }

  stage('Build') {
    /* Call the ant build. */
    ant "info"
  }

  stage('Verify') {
    /* Requires a separate job which polls then reads workspace */
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
