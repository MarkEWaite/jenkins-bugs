#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def repo_url='https://github.com/MarkEWaite/jenkins-bugs'
def branch='JENKINS-40050'

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              /* Bug report requires jgit as gitTool */
              gitTool: 'jgit',

              branches: [[name: "${branch}"]],
              browser: [$class: 'GithubWeb', repoUrl: "${repo_url}"],
              userRemoteConfigs: [[name: 'origin',
                                  refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}",
                                  url: "${repo_url}"]],
              extensions: [
                            [$class: 'CloneOption',
                              depth: 0,
                              honorRefspec: true,
                              noTags: true,
                              reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                              shallow: false,
                              timeout: 8],
                            [$class: 'LocalBranch', localBranch: "${branch}"],
                          ],
             ]
            )
  }

  stage('Build') {
    /* Call the ant build. */
    def step = new com.markwaite.Build()
    step.ant "info"
  }

  stage('Verify') {
    def check = new com.markwaite.Assert()
    check.logDoesNotContain(".*TranslationBundleLoadingException.*", "Translation bundle loading exception thrown")
  }
}
