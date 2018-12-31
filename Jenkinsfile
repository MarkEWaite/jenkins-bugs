pipeline {
    options {
        durabilityHint('PERFORMANCE_OPTIMIZED')
        skipDefaultCheckout()
        timestamps()
    }
    agent {
        label 'windows'
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
		withAnt(installation: 'ant-latest') {
		    bat 'ant info'
		}
            }
        }
    }
}
