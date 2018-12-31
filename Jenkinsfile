pipeline {
    agent {
        label 'windows'
    }
    options {
        durabilityHint('PERFORMANCE_OPTIMIZED')
        skipDefaultCheckout()
        timestamps()
    }
    triggers {
        pollSCM('H/13 * * * *')
    }
    tools {
        ant 'ant-latest'
    }
    stages {
        stage('Info') {
            steps {
                checkout([
		  $class: 'GitSCM',
		  branches: scm.branches,
		  extensions: scm.extensions,
		  userRemoteConfigs: scm.userRemoteConfigs
                ])
                bat 'ant info'
            }
        }
    }
}
