package com.markwaite;

/* Assert build log contains string matching regex */
def logContains(String regex, String failure_message) {
  if (!manager.logContains(regex)) {
    manager.addWarningBadge(failure_message)
    manager.createSummary("warning.gif").appendText("<h1>" + failure_message + "</h1>", false, false, false, "red")
    manager.buildUnstable()
  }
}

/* Assert build log does not contain string matching regex */
def logDoesNotContain(String regex, String failure_message) {
  if (manager.logContains(regex)) {
    manager.addWarningBadge(failure_message)
    manager.createSummary("warning.gif").appendText("<h1>" + failure_message + "</h1>", false, false, false, "red")
    manager.buildUnstable()
  }
}
