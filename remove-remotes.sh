#!/bin/sh

# Intentionally did not define the cache repository so that no notifyCommit
# will be called for commits to this branch.  GitHub webhooks are not
# currently configured to reach inside my private network, so changes
# on this branch should only be detected by the polling defined in the
# Jenkinsfile.

# The typical process to create this repository will add a cache and
# a bare remote.  This script removes them so that the test environment
# matches the original verification attempt.

git remote | grep -q -w cache && git remote rm cache
git remote | grep -q -w bare && git remote rm bare

# Redefine the `push-a` git alias to only push to origin
git config alias.push-a push

exit 0
