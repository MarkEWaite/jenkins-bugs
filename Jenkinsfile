pipeline {
  agent none
  options {
    durabilityHint('MAX_SURVIVABILITY')
  }
  stages {
    stage('parallel') {
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
            echo 'Workspace before windows ws is ' + WORKSPACE
            ws(dir: WORKSPACE + '/windows-dir') {
              echo 'Workspace inside windows ws is ' + WORKSPACE
            }
            echo 'Workspace after ws windows is ' + WORKSPACE
            bat 'echo hello windows from %COMPUTERNAME%'
            bat 'if not exist build.xml exit 1'
          }
        }
        stage('linux') {
          agent {
            label 'linux'
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
            echo 'Workspace before linux ws is ' + WORKSPACE
            ws(dir: WORKSPACE + '/linux-dir') {
              echo 'Workspace inside linux ws is ' + WORKSPACE
            }
            echo 'Workspace after linux ws is ' + WORKSPACE
            sh 'echo hello linux from `hostname`'
            sh '[ -f build.xml ] || exit 1'
          }
        }
      }
    }
  }
}
