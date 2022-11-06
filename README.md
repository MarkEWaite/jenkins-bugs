# [JENKINS-70024](https://issues.jenkins.io/browse/JENKINS-70024) cron trigger not retained

User expects that the branch specific cron trigger defined in the Jenkinsfile continues no matter which branch runs the cron step.
The call to cron('') seems suspicious to me.
