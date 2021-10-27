#!groovy

/* Only keep the 5 most recent builds. */
properties([buildDiscarder(logRotator(numToKeepStr: '5'))])

def branch = 'JENKINS-66651'

def expansion = ''
def expansionTrue = ''
def expansionEmpty = ''
def buildnum = ''

node {
  stage('Checkout') {
      checkout([$class: 'GitSCM',
                  branches: [[name: branch]],
                  extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                               [$class: 'LocalBranch', localBranch: branch]
                              ],
                  gitTool: scm.gitTool,
                  userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: 'https://github.com/MarkEWaite/jenkins-bugs.git']]])
      expansion = tm '${GIT_BRANCH,fullName=false}'
      expansionTrue = tm '${GIT_BRANCH,fullName=true}'
      expansionEmpty  = tm '${GIT_BRANCH}'
      buildnum = tm('${BUILD_NUMBER}')
      echo('buildnum is ' + buildnum)
  }

  stage('Verify') {
    if (expansion != branch) {
      echo("expansion is ${expansion}")
      failure_message = "GIT_BRANCH was '${expansion}', expected '" + branch + "'"
      manager.addWarningBadge(failure_message)
      manager.createSummary("warning.gif").appendText("<h1>" + failure_message + "</h1>", false, false, false, "red")
      manager.buildUnstable()
    }
    if (expansionEmpty != branch) {
      echo("expansionEmpty is ${expansionEmpty}")
      failure_message = "GIT_BRANCH was '${expansionEmpty}', expected '" + branch + "'"
      manager.addWarningBadge(failure_message)
      manager.createSummary("warning.gif").appendText("<h1>" + failure_message + "</h1>", false, false, false, "red")
      manager.buildUnstable()
    }
    if (expansionTrue != ('origin/'+branch)) {
      echo("expansionTrue is ${expansionTrue}")
      failure_message = "GIT_BRANCH was '${expansionTrue}', expected 'origin/" + branch + "'"
      manager.addWarningBadge(failure_message)
      manager.createSummary("warning.gif").appendText("<h1>" + failure_message + "</h1>", false, false, false, "red")
      manager.buildUnstable()
    }
  }
}
