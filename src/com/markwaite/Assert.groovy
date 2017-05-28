package com.markwaite;

def assertCondition(boolean condition, String failure_message) {
  if (!condition) {
    manager.addWarningBadge(failure_message)
    manager.createSummary("warning.gif").appendText("<h1>" + failure_message + "</h1>", false, false, false, "red")
    manager.buildUnstable()
  }
}

/* Assert build log contains string matching regex */
def logContains(String regex, String failure_message) {
  assertCondition(manager.logContains(regex), failure_message)
}

/* Assert build log contains exactly count strings matching regex */
def logContainsCount(int expectedCount, String regexp, String failure_message) {
  java.util.regex.Matcher matcher = getLogMatcher(regexp)
  int count = 0
  while (matcher.find()) {
    count++
  }
  assertCondition(count == expectedCount, "Found " + count + ", expected " + expectedCount. " + failure_message)
}

/* Assert build log does not contain string matching regex */
def logDoesNotContain(String regex, String failure_message) {
  assertCondition(!manager.logContains(regex), failure_message)
}
