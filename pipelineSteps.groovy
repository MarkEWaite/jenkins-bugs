/**
 * Various pipeline steps for Jenkinsfile use
 */

import com.cloudbees.groovy.cps.NonCPS

def version = '1.0'

@NonCPS
def dumpEnvVars() {
  def str = "Dumping build environment variables...\n"
  for (Map.Entry<String, String> entry : currentBuild.build().environment) {
    str += "    ${entry.key} = ${entry.value}\n"
  }
  echo str
}

/* Run ant from tool "ant-latest" */
void ant(def args) {
  /* Get jdk tool. */
  String jdktool = tool name: 'jdk21', type: 'hudson.model.JDK'

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

def getSHA1(def commit) {
  if (isUnix()) {
    sha1 = sh(script: "git rev-parse ${commit}", returnStdout: true)
  } else {
    // Windows treats caret as special character, must escape it
    if (commit.contains("^")) {
      commit = commit.replace("^", "^^")
    }
    // Windows returns command line before sha1 unless we ECHO OFF prior
    sha1 = bat(script: "@ECHO OFF && git rev-parse ${commit}", returnStdout: true)
  }
  // Remove white space
  sha1 = sha1.replaceAll("\\s", "")
  if (sha1.length() > 40) {
    sha1 = sha1.substring(sha1.length() - 40)
  }
  return sha1
}

return this;
