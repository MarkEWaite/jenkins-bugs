# [ZD-59897](https://cloudbees.zendesk.com/agent/tickets/59897) - Git checkout fails sporadically in a declarative pipeline

Report that several times a day a job will fail with the message

    hudson.plugins.git.GitException: Command "/usr/bin/git checkout -f deadbeefbeadadddad" returned status code 128: 
    stdout:
    stderr: fatal: reference is not a tree: deadbeefbeadadddad

The 'deadbeefbeadadddad' is an obfuscated version of the actual SHA1 from the report.

