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
                                            [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '/var/lib/git/mwaite/bare/bugs/jenkins-bugs.git'],
                                            [$class: 'AuthorInChangelog'],
                                           ],
                               gitTool: 'Default',
                               submoduleCfg: [],
                               userRemoteConfigs: [[refspec: '+refs/heads/JENKINS-52746:refs/remotes/origin/JENKINS-52746', url: 'https://github.com/MarkEWaite/jenkins-bugs']]])
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
