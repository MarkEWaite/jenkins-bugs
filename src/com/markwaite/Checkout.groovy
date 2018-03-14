package com.markwaite;

void checkout(def branch) {
  def bugsRepoUrl = 'https://github.com/MarkEWaite/jenkins-bugs'
  checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              browser: [$class: 'GithubWeb', repoUrl: bugsRepoUrl],
              extensions: [
                  [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/bugs/jenkins-bugs.git'],
                  [$class: 'LocalBranch', localBranch: branch],
              ],
              gitTool: scm.gitTool,
              userRemoteConfigs: [[name: 'origin', refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: bugsRepoUrl]]])
}
