pipeline {
    options {
        timestamps()
        skipDefaultCheckout()
    }
    agent {
        label 'windows'
    }
    stages {
        stage('Info') {
            steps {
                // git 'https://github.com/amuniz/maven-helloworld'
                // git branch: 'JENKINS-55257', url: 'https://github.com/MarkEWaite/jenkins-bugs'
                checkout([
                  $class: 'GitSCM',
                  branches: scm.branches,
                  extensions: scm.extensions,
                  userRemoteConfigs: [refspec: '+refs/heads/' + scm.branches[0] + ':refs/remotes/origin/' + scm.branches[0], url: scm.userRemoteConfigs[0].url]
                ])
                withAnt(installation: 'ant-latest') {
                    bat 'ant info'
                }
            }
        }
    }
}
