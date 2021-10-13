#! /usr/bin/python

import optparse
import os
import random
import re
import subprocess
import shutil
import sys

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

    # keep at optparse for 2.6. compatibility
    # parser.add_option("-c", "--clean", action="store_true", default=False, help="clean prior file system image")

    options, arg_hosts = parser.parse_args()

    count = 0
    for commit_message in commit_messages():
        if commit_message.strip() == "":
            continue
        if re.match("^[ ./:,A-Za-z0-9_-]+$", commit_message):
            print("Skipped commit message '" + commit_message + "'")
            continue
        count = count + 1
        git_command = [ "git", "commit",
                        "--allow-empty",
                        "-m", "Commit count " + str(count),
                        # "-m", commit_message
                      ]
        subprocess.check_call(git_command)

#-----------------------------------------------------------------------

if __name__ == "__main__": submit_changes(sys.argv[1:])
