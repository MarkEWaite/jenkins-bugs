#!/usr/bin/env groovy

/* Assert build log contains string matching regex */
void logContains(String regex, String failure_message) {
  if (!manager.logContains(regex)) {
    manager.addWarningBadge(failure_message)
    manager.createSummary("warning.gif").appendText("<h1>" + failure_message + "</h1>", false, false, false, "red")
    manager.buildUnstable()
  }
}
