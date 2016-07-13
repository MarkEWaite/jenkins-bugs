#! /usr/bin/python

import optparse
import os
import re
import subprocess
import shutil
import sys

import json

#-----------------------------------------------------------------------

def commit_messages():
    with open("bad/blns.json") as data_file:
        data = json.load(data_file)
    data.append("abcdef")
    data.append("xyz")
    return data

#-----------------------------------------------------------------------

def submit_changes(args = []):
    help_text = """%prog [options] [host(s)]
Submit problem change log messages to a git repo.   Use -h for help."""
    parser = optparse.OptionParser(usage=help_text)

    # keep at optparse for 2.6. compatibility
    # parser.add_option("-c", "--clean", action="store_true", default=False, help="clean prior file system image")

    options, arg_hosts = parser.parse_args()

    messages = commit_messages()
    for commit_message in messages:
        if commit_message == "":
            continue
        print(commit_message)
        git_command = [
                        "git",
                        "commit",
                        "--allow-empty",
                        "-m", commit_message
                      ]
        subprocess.check_call(git_command)

#-----------------------------------------------------------------------

if __name__ == "__main__": submit_changes(sys.argv[1:])
