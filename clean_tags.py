#! /usr/bin/python

import optparse
import os
import subprocess
import sys

#-----------------------------------------------------------------------

def get_redundant_tags():
    tags = subprocess.check_output(["git", "tag", "--list", "--sort=version:refname"]).splitlines()
    redundant_tags = filter(lambda x: 'ZD-64922-' in x, tags)
    return redundant_tags[:-1] # Return all but the last tag

#-----------------------------------------------------------------------

def delete_tags(tags):
    if not tags:
        return
    print("Deleting local tags: " + ' '.join(tags))
    for tag in tags:
        subprocess.check_call(["git", "tag", "--delete", tag])
    print("Deleting tags in bare repo: " + ' '.join(tags))
    subprocess.call(["git", "push", "--delete", "bare"] + tags)
    print("Deleting tags in cache repo: " + ' '.join(tags))
    subprocess.call(["git", "push", "--delete", "cache"] + tags)
    print("Deleting remote tags: " + ' '.join(tags))
    subprocess.call(["git", "push", "--delete", "origin"] + tags)

#-----------------------------------------------------------------------

def clean_tags(args = []):
    help_text = """%prog [options]
Remove redundant git tags from remote repos. Use -h for help."""
    parser = optparse.OptionParser(usage=help_text)

    options = parser.parse_args()

    redundant_tags = get_redundant_tags()
    delete_tags(redundant_tags)

#-----------------------------------------------------------------------

if __name__ == "__main__": clean_tags(sys.argv[1:])
