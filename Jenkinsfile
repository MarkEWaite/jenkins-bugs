#!groovy

@Library(value='globalPipelineLibraryMarkEWaiteModernGitHub@v1.1', changelog=false) _
import com.markwaite.Assert
import com.markwaite.Build

node {
  stage('Checkout') {
    checkout([$class: 'GitSCM',
                branches: [[name: 'JENKINS-31828']],
                extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                             [$class: 'LocalBranch', localBranch: '**'],
                             [$class: 'AuthorInChangelog']],
                gitTool: scm.gitTool,
                userRemoteConfigs: [[refspec: '+refs/heads/JENKINS-15103:refs/remotes/origin/JENKINS-15103 +refs/heads/JENKINS-31828:refs/remotes/origin/JENKINS-31828 +refs/heads/master:refs/remotes/origin/master', url: 'git://github.com/MarkEWaite/jenkins-bugs.git']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-31828 reports that multiple refspecs causes polling to fail to detect changes.  */
    my_check.logContains('.*=== End of git log output.*', 'Git log end missing')
  }
}
