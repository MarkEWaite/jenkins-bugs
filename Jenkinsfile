pipeline {
    options {
        skipDefaultCheckout()
    }
    environment {
        HTTP_RESPONSE = httpRequest httpMode: 'GET',
                                    consoleLogResponseBody: false,
                                    validResponseContent: 'https://github.com/MarkEWaite', 
                                    quiet: true,
                                    authentication: 'invalid-user-and-password',
                                    url: 'https://api.github.com/users/MarkEWaite'
        URL_SUFFIX = 'manage/credentials/store/system/domain/_/credential/invalid-user-and-password/'
    }
    agent none
    stages {
        stage('Track credentials') {
            steps {
                echo env.HTTP_RESPONSE
                echo 'Open ' + env.JENKINS_URL + env.URL_SUFFIX + ' to confirm this job is using the credential'
            }
        }
    }
}
