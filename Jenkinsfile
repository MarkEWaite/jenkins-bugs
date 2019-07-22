#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def branch = 'JENKINS-52059'

node {
  def scmVars
  def firstSHA1
  stage('Checkout & Build') {
    scmVars = checkout([$class: 'GitSCM',
                branches: scm.branches,
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                            ],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs',
                                    refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}"]]])
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info' /* Message from first checkout */	  
    firstSHA1 = my_step.getSHA1('HEAD')
    echo "First SHA1 is ${firstSHA1}"
    /* Check the environment from the checkout */
    def my_check = new com.markwaite.Assert()
    my_check.assertCondition(env.GIT_COMMIT != null, "env.GIT_COMMIT from checkout is null")
    my_check.logContains(".*Git HEAD is ${env.GIT_COMMIT}.*", "Missing env.GIT_COMMIT in log, expected SHA1 '${env.GIT_COMMIT}'")
    my_check.assertCondition(firstSHA1 == env.GIT_COMMIT, "first computed '${firstSHA1}' != first returned env '${env.GIT_COMMIT}'")
  }

  // stage('Verify') {
  //   /* Check the return value from the checkout */
  //   def my_check = new com.markwaite.Assert()
  //   my_check.assertCondition(scmVars.GIT_COMMIT != null, "GIT_COMMIT from checkout is null")
  //   my_check.logContains(".*Git HEAD is ${scmVars.GIT_COMMIT}.*", "Missing scmVars GIT_COMMIT in log, expected SHA1 '${scmVars.GIT_COMMIT}'")
  //   my_check.assertCondition(firstSHA1 == scmVars.GIT_COMMIT, "first computed '${firstSHA1}' != first returned '${scmVars.GIT_COMMIT}'")
  // }
}
