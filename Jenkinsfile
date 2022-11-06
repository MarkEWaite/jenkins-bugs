CRON_SETTINGS = BRANCH_NAME == 'JENKINS-70024' ? '0 0 * * 0' : ''
pipeline {
    options {
        skipDefaultCheckout()
    }
    triggers {
         cron(CRON_SETTINGS)
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
                echo 'http response is ' + HTTP_RESPONSE
                echo 'Open ' + JENKINS_URL + URL_SUFFIX + ' to see credential is used by the job'
            }
        }
    }
}
