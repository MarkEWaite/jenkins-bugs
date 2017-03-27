#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

def branch = 'JENKINS-15103'
def origin = "${branch}-origin"
def repo = 'https://github.com/MarkEWaite/jenkins-bugs'

node('windows') {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: "${origin}/${branch}*"]], /* Trailing '*' required to see bug */
              browser: [$class: 'GithubWeb', repoUrl: "${repo}"],
              extensions: [
                [$class: 'CloneOption', honorRefspec: true, noTags: true],
                [$class: 'WipeWorkspace'] /* WipeWorkspace causes the failure due to busy pack file */
              ],
              gitTool: 'jgit',
              userRemoteConfigs: [[name: "${origin}", refspec: "+refs/heads/${branch}:refs/remotes/${origin}/${branch}", url: "${repo}"]]
             ]
            )
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    my_check.logContains('.*user dir is .*', 'Ant output missing user dir report')
  }
}
