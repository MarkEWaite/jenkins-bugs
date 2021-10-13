#! /usr/bin/python

import optparse
import os
import random
import re
import subprocess
import shutil
import sys
import uuid

import json

#-----------------------------------------------------------------------

def commit_messages():
    with open("bad/blns.json") as data_file:
        data = json.load(data_file)
    random.shuffle(data)
    return data[0:20]

#-----------------------------------------------------------------------

def submit_changes(args = []):
    help_text = """%prog [options]
Submit problem change log messages to a git repo.   Use -h for help."""
    parser = optparse.OptionParser(usage=help_text)
    parser.add_option("-e", "--empty-commits", action="store_true", default=False, help="Use empty commits without file content changes")

    options, arg_hosts = parser.parse_args()

    for commit_message in commit_messages():
        if commit_message.strip() == "":
            continue
        if re.match("^[ ./:,A-Za-z0-9_-]+$", commit_message):
            print("Skipped commit message '" + commit_message + "'")
            continue
        if not options.empty_commits:
            # JENKINS-66885 notes that changes are not reported for empty commits.
            # An empty commit is a commit that has a commit message but changes no file.
            filename = str(uuid.uuid4())
            with open(filename, 'w+') as f:
                f.write(commit_message)
            subprocess.check_call([ 'git', 'add', filename])

        git_command = [ "git", "commit",
                        "-m", commit_message,
                        "--allow-empty"
                      ]
        subprocess.check_call(git_command)

    if not options.empty_commits:
        subprocess.check_call([ 'git', 'rm', '*-*-*-*-*'])
        git_command = [ "git", "commit",
                        "-m", 'Remove temporary files',
                      ]
        subprocess.check_call(git_command)

#-----------------------------------------------------------------------

if __name__ == "__main__": submit_changes(sys.argv[1:])
