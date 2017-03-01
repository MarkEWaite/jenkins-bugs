#!groovy

@Library('globalPipelineLibraryMarkEWaite')
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
  stage('Checkout') {
  checkout([$class: 'GitSCM',
            branches: [[name: 'origin-JENKINS-35501/JENKINS-35501']],
            browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
            extensions: [[$class: 'CloneOption',
                          depth: 0,
                          honorRefspec: true,
                          noTags: true,
                          reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                          shallow: true,
                          timeout: 7],
                        [$class: 'AuthorInChangelog']],
            userRemoteConfigs: [[name: 'origin-JENKINS-35501',
                                refspec: '+refs/heads/JENKINS-35501:refs/remotes/origin-JENKINS-35501/JENKINS-35501 ',
                                url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-35501 reports the .gitattributes file is ignored. */
    my_check.logContains('.*nothing to commit.*working .* clean.*', 'Ant modified files unexpectedly')
  }
}
