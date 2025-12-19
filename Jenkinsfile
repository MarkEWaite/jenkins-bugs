#!groovy

@Library('globalPipelineLibraryMarkEWaite') _
import com.markwaite.Assert
import com.markwaite.Build

/* Only keep the 10 most recent builds. */
properties([buildDiscarder(logRotatornumToKeepStr: '10')])

def branch='JENKINS-21248'
def repo_url=scm.userRemoteConfigs[0].url

node('git-2.30+') { // Needs 'git -C' argument support, sporadically fails on git 2.7

  /* default depth should clone 1 commit */
  stage('Checkout default depth') {
    deleteDir() // Really scrub the workspace
    // Shallow checkout parent and submodule - default depth 1
    checkout scmGit(
              branches: [[name: branch]],
              extensions: [cloneOption(honorRefspec: true, noTags: true, shallow: true, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'),
                           submodule(shallow: true),
                           localBranch(branch)],
              gitTool: 'Default', // JGit does not support shallow clone for submodules
              userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repo_url]])
  }

  stage('Build default depth') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify default depth') { // Confirm depth is 1 in submodule history
    def my_check = new com.markwaite.Assert()

    /* JENKINS-21248 requests shallow clone support for submodules.  */
    my_check.logContains('.*Using shallow .* with depth 1.*', 'Missing depth 1 log message')
    my_check.logContains('.*Using shallow submodule update with depth 1.*', 'Missing depth 1 submodule log message')
    my_check.logContains('.*Reduce title length.*', 'Default distinctive 1st commit message not found')
    my_check.logDoesNotContain('.*Link from README to bug report.*', 'Distinctive 2nd commit message found')
    my_check.logDoesNotContain('.*Add more text to README.*', '2 - Distinctive 3rd commit message found')

    /* Check submodule exists */
    my_check.logContains('.*check-dir property module.git.dir.exists is true.*', 'Ant did not find modules .git')
    my_check.logDoesNotContain('.*check-dir property module.git.dir.exists is .*module.git.dir.exists.*', 'Ant check-dir did not set submodule detected property')
    echo "================= End of Verify Depth 1 ================="
  }

  /* depth 2 should clone 2 commits */
  stage('Checkout depth 2') {
    echo "================= Begin Checkout Depth 2 ================="
    deleteDir() // Really scrub the workspace
    /* May fail if new commits have been added to the underlying branch of the submodule */
    /* If the submodule reference does not refer to a branch, then the remote github server refuses to respond to the request.
     * Newer versions of command line git then report the message:
     *
     * error: Server does not allow request for unadvertised object 0736ba35a0d8c05236e3b71584bc4e149aa5f10a
     */
    checkout scmGit(
              branches: [[name: branch]],
              extensions: [cloneOption(honorRefspec: true, noTags: true, shallow: true, depth: 2, reference: '/var/lib/git/mwaite/bugs/jenkins-bugs.git'),
                           submodule(shallow: true, depth: 2),
                           localBranch(branch)],
              gitTool: 'Default',
              userRemoteConfigs: [[refspec: "+refs/heads/${branch}:refs/remotes/origin/${branch}", url: repo_url]])
  }

  stage('Build depth 2') {
    /* Call the ant build. */
    def my_step = new com.markwaite.Build()
    my_step.ant 'info'
  }

  stage('Verify depth 2') { // Confirm depth is 2 instead of 1 in submodule history
    def my_check = new com.markwaite.Assert()

    /* JENKINS-21248 requests shallow clone support for submodules.  */
    my_check.logContains('.*Using shallow .* with depth 2.*', 'Missing depth 2 log message')
    my_check.logContains('.*Using shallow submodule update with depth 2.*', 'Missing depth 2 submodule log message')
    my_check.logContains('.*Reduce title length.*', '2 - Distinctive 1st commit message not found')
    my_check.logContains('.*Add distinctive message in submodule README.*', '2 - Distinctive 2nd commit message not found')
    my_check.logDoesNotContain('.*Add more text to README.*', '2 - Distinctive 3rd commit message found')

    /* Check submodule exists */
    my_check.logContains('.*check-dir property module.git.dir.exists is true.*', '2 - Ant did not find modules .git')
    my_check.logDoesNotContain('.*check-dir property module.git.dir.exists is .*module.git.dir.exists.*', '2 - Ant check-dir did not set submodule detected property')
    echo "================= End of Verify Depth 2 ================="
  }

}
