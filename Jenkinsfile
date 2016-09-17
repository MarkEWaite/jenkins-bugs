#!groovy

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
	      branches: [[name: 'origin/JENKINS-34309']],
	      browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
	      extensions: [[$class: 'CloneOption',
			    honorRefspec: true,
			    noTags: true,
			    reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
			    shallow: true,
			    timeout: 3],
			   [$class: 'LocalBranch', localBranch: 'JENKINS-34309'],
			   [$class: 'AuthorInChangelog']],
	      gitTool: 'Default',
	      userRemoteConfigs: [[credentialsId: 'MarkEWaite-github-rsa-private-key',
				   name: 'origin',
				   refspec: '+refs/heads/JENKINS-34309:refs/remotes/origin/JENKINS-34309',
				   url: 'git@github.com:MarkEWaite/jenkins-bugs']]])
  }

  stage('Build') {
    /* Call the ant build. */
    ant "info"
  }

  stage('Verify') {
    if (!manager.logContains(".*[*] JENKINS-34309")) { // Confirm LocalBranch extension worked
      manager.addWarningBadge("Missing JENKINS-34309 branch name.")
      manager.createSummary("warning.gif").appendText("<h1>Missing JENKINS-34309 branch name!</h1>", false, false, false, "red")
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
