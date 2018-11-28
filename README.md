# [JENKINS-23606](https://issues.jenkins-ci.org/browse/JENKINS-23606) - merge commit changes report more than actually changed

User has projects defined as subdirectories within their git repository.

Each subdirectory has a job defined which limits itself to only see changes in the regions which the job defines as "included".
When a merge is committed, all jobs are triggered, even if the merge only changes files in one of the subdirectories.

To duplicate the bug, try the following steps:

1. Create a base branch named '[JENKINS-23606](https://github.com/MarkEWaite/jenkins-bugs/tree/JENKINS-23606)' with subdirectories project-1 and project-2
2. Create a freestyle job monitoring 'JENKINS-23606' with no region exclusions
3. Create a project branch 'JENKINS-23606-project-1' which will only make changes in the project-1 subdirectory
4. Create a freestyle job monitoring 'JENKINS-23606-project-1' which only monitors changes in the project-1 subdirectory
5. Create a project branch 'JENKINS-23606-project-2' which will only make changes in the project-2 subdirectory
6. Create a freestyle job monitoring 'JENKINS-23606-project-2' which only monitors changes in the project-2 subdirectory
7. Commit to the base branch and confirm that only the base branch job runs
8. Commit to the project-1 branch and confirm that only the project-1 branch job runs
9. Commit to the project-2 branch and confirm that only the project-2 branch job runs
10. Merge from project-1 to the base branch and confirm that only the base branch job runs
