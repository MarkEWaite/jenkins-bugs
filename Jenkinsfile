#!groovy

@Library(value='globalPipelineLibraryMarkEWaiteModernGitHub@v1.1', changelog=false) _
import com.markwaite.Assert
import com.markwaite.Build

// Narrow the respec to only this branch
def branch = 'JENKINS-68751'
def myRemoteConfigs = scm.userRemoteConfigs
myRemoteConfigs[0].refspec = myRemoteConfigs[0].refspec.replace('*', branch)

/* Only keep the 10 most recent builds. */
properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node('linux && !cloud') { // Needs curl installed, needs local access to Jenkins server
  stage('Checkout') {
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              extensions: [[$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'],
                           [$class: 'LocalBranch', localBranch: '**'],
                           [$class: 'CleanCheckout'], // ant info clutters workspace with output files
                          ],
              gitTool: scm.gitTool,
              userRemoteConfigs: myRemoteConfigs
            ])

    // Report contents of changeset
    def changeLogSets = currentBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
      def entries = changeLogSets[i].items
      for (int j = 0; j < entries.length; j++) {
        def entry = entries[j]
        echo "OUT: ${entry.commitId} by ${entry.author} on ${new Date(entry.timestamp)}: ${entry.msg}"
        def files = new ArrayList(entry.affectedFiles)
        echo "OUT: beginning of ${files.size()} affected files "
        for (int k = 0; k < files.size(); k++) {
          def file = files[k]
          echo "OUT: ${file.editType.name} ${file.path}"
        }
        echo "OUT: end of ${files.size()} affected files "
      }
    }
  }

  stage('Build') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify') {
    def my_check = new com.markwaite.Assert()
    /* JENKINS-68751 reports Pipeline checkout cannot use non-global scoped credentials.  */
    my_check.logContains('.*reportScanLogResults script exited normally.*',  'branch scan test script unexpected exit')
    my_check.logContains('.*Branch scan log .* contains expected content.*', 'Branch scan not authenticated')
  }
}
