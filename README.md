# [JENKINS-60591](https://issues.jenkins.io/browse/JENKINS-60591) Delayed checkout pulls latest, not originating commit

Bug is described as:

1. Commit to branch and push to central repository (first SHA-1)
2. Confirm that job is started by push to central repository and is waiting for input
3. Commit to branch again (second SHA-1)
4. Answer job's request for input
5. Confirm checkout inside the job used the first SHA-1 and not the second SHA-1

Expected result: Checkout should use first SHA-1, not second SHA-1 

Reported result: Checkout used second SHA-1
