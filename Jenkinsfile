#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-34042'
def repo_url = scm.userRemoteConfigs[0].url

node {
  stage('Checkout') {
    def my_check = new com.markwaite.Assert()
    def exception_message = "not empty"
    def exception_class_name = "unknown"
    try {
      checkout([$class: 'GitSCM',
                  branches: [[name: branch]],
                  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                               [$class: 'LocalBranch', localBranch: branch]
                              ],
                  gitTool: scm.gitTool,
                  userRemoteConfigs: [[refspec: 'intentionally-invalid-refspec', url: repo_url ]]])
      my_check.assertCondition(false, "Unexpected location 1 in Jenkinsfile")
    } catch (Exception e) {
      exception_class_name = e.class.name
      exception_message = e.getMessage()
    }
    my_check.assertCondition(!exception_message.isEmpty(), "Empty exception message for class ${exception_class_name}")
    my_check.assertCondition(exception_message != "not empty", "Uninitialized exception message ${exception_message}")
    echo "Exception message is '${exception_message}'"
  }
}
