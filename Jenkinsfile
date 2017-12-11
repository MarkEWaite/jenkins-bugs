#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

def platforms = [ 'windows', 'linux' ]

def tasks = [ : ]

for (int i = 0; i < platforms.size(); ++i) {
  def label = platforms[i]
  tasks[label] = {
    node("$label && git-1.9+") { // Needed for shallow clone
      stage("Checkout $label") {
      checkout([$class: 'GitSCM',
                branches: [[name: 'origin-JENKINS-35501/JENKINS-35501']],
                browser: [$class: 'GithubWeb', repoUrl: 'https://github.com/MarkEWaite/jenkins-bugs'],
                extensions: [[$class: 'CloneOption',
                              depth: 1,
                              honorRefspec: true,
                              noTags: true,
                              reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git',
                              shallow: true,
                              timeout: 7],
                             [$class: 'CleanCheckout'],
                            ],
                userRemoteConfigs: [[name: 'origin-JENKINS-35501',
                                    refspec: '+refs/heads/JENKINS-35501:refs/remotes/origin-JENKINS-35501/JENKINS-35501 ',
                                    url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
      }

      stage("Build $label") {
        /* Call the ant build. */
        def my_step = new com.markwaite.Build()
        my_step.ant 'info'
      }

      stage("Verify $label") {
        def my_check = new com.markwaite.Assert()
        /* JENKINS-35501 reports the .gitattributes file is ignored. */
        my_check.logContains('.*nothing to commit.*working .* clean.*', 'Found something to commit')
        my_check.logDoesNotContain('.*modified: .*', 'Ant modified files unexpectedly')
        my_check.logDoesNotContain('.*not staged for commit.*', 'Changes not staged for commit')
      }
    }
  }
}

parallel(tasks)
