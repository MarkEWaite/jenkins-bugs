#!groovy

/* Only keep the 10 most recent builds. */
properties([buildDiscarder(logRotator(numToKeepStr: '10'))])

def branch = 'JENKINS-66651'

def expansion = ''
def buildnum = ''

node('!windows') {
  stage('Checkout') {
      checkout([$class: 'GitSCM',
                  branches: [[name: branch]],
                  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                               [$class: 'LocalBranch', localBranch: branch]
                              ],
                  gitTool: scm.gitTool,
                  userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
      expansion = tm '${GIT_BRANCH,fullName=false}'
      buildnum = tm('${BUILD_NUMBER}')
      def expansionTrue = tm '${GIT_BRANCH,fullName=true}'
      def expansionEmpty  = tm '${GIT_BRANCH}'
      echo('expansion is ' + expansion)
      echo('expansionTrue is ' + expansionTrue)
      echo('expansionEmpty is ' + expansionEmpty)
      echo('buildnum is ' + buildnum)
  }

  stage('Verify') {
    if (expansion != branch) {
      failure_message = "GIT_BRANCH was '${expansion}', expected '" + branch + "'"
      manager.addWarningBadge(failure_message)
      manager.createSummary("warning.gif").appendText("<h1>" + failure_message + "</h1>", false, false, false, "red")
      manager.buildUnstable()
    }
  }
}
