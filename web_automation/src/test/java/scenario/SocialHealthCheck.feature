Feature: Verification that social channel is UP

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4519")
  Scenario: Check social is up
    When Make get request for social health check returns 200 status code