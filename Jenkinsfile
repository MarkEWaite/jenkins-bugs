pipeline {
  agent none
  options {
    durabilityHint('MAX_SURVIVABILITY')
  }
  stages {
    stage('JENKINS-59785') {
      parallel {
        stage('windows') {
          agent { 
            label 'windows'
          }
          when {
            anyOf {
              changeset "build*r"
              changeset "LICENSE"
              changeset "README*"
              changeset "build*l"
              changeset "*.xml"
              changeset "build.*"
              changeset "*.number"
              changeset "*num*r"
            }
          }
          steps {
            ws(dir: WORKSPACE + '/windows-dir') {
              echo 'Workspace inside windows ws is ' + WORKSPACE
            }
            bat 'echo hello windows from %COMPUTERNAME% && dir build.xml'
            bat 'if not exist build.xml exit 1'
          }
        }
        stage('unix') {
          agent {
            label '!windows'
          }
          when {
            anyOf {
              changeset "LICENSE"
              changeset "README*"
              changeset "R*md"
              changeset "RE*md"
              changeset "REA*md"
              changeset "READ*md"
              changeset "READM*md"
              changeset "README.md"
              changeset "*.xml"
              changeset "*.number"
              changeset "*.n*r"
            }
          }
          steps {
            ws(dir: WORKSPACE + '/unix-dir') {
              echo 'Workspace inside unix ws is ' + WORKSPACE
            }
            sh 'echo hello unix from `hostname`; ls build.xml'
            sh '[ -f build.xml ] || exit 1'
          }
        }
      }
    }
  }
}
