pipeline {
    agent {
        label '!windows'
    }
    options {
        timeout(time: 4, unit: 'HOURS')
        timestamps()
        buildDiscarder(logRotator(artifactDaysToKeepStr: '2', artifactNumToKeepStr: '5', daysToKeepStr: '15', numToKeepStr: '60'))
    }
    triggers {
        pollSCM('H/2 * * * *')
    }
    stages {
        stage('Checkout') {
            steps {
                checkout(poll: true,
                         scm: [$class: 'GitSCM',
                               branches: [[name: 'refs/heads/JENKINS-52746']],
                               doGenerateSubmoduleConfigurations: false,
                               extensions: [
                                            [$class: 'GitLFSPull'],
                                            [$class: 'AuthorInChangelog'],
                                            [$class: 'RelativeTargetDirectory', relativeTargetDir: '.'],
                                            [$class: 'PruneStaleBranch'],
                                           ],
                               gitTool: 'Default',
                               submoduleCfg: [],
                               userRemoteConfigs: [[url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
            }
        }
        stage('Test and Package') {
            steps {
                sh 'ls'
            }
        }
    }
    post {
        always {
            deleteDir()
        }
    }
}
