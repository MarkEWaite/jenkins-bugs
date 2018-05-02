#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 17 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '17']]])

node('linux') {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: 'ZD-60033']],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: 'ZD-60033']],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[refspec: '+refs/heads/ZD-60033:refs/remotes/origin/ZD-60033', url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
    withCredentials([usernameColonPassword(credentialsId: 'MarkEWaite-github-username-password', variable: 'USERNAME_PLUS_PASSWORD')]) {
      dir('tasks-username-colon-password') {
        deleteDir()
        sh '''echo USERNAME_PLUS_PASSWORD=$USERNAME_PLUS_PASSWORD
              git clone https://$USERNAME_PLUS_PASSWORD@github.com/MarkEWaite/tasks.git tasks-$$
              ls
              pwd
              cd tasks-$$
              git status
              git branch
              git config remote.origin.url
        '''
      }
    }
    withCredentials([usernamePassword(credentialsId: 'MarkEWaite-github-username-password', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
      dir('tasks-username-password') {
        deleteDir()
        sh '''echo USERNAME=$USERNAME
              echo PASSWORD=$PASSWORD
              git clone https://$USERNAME:$PASSWORD@github.com/MarkEWaite/tasks.git tasks-$$
              ls
              pwd
              cd tasks-$$
              git status
              git branch
              git config remote.origin.url
              GIT_ASKPASS=./git_askpass.sh git pull
           '''
      }
    }
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*[*] ZD-60033.*', 'Wrong branch reported')
  }
}
