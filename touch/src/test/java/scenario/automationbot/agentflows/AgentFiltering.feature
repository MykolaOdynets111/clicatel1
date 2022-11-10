Feature: Filtering : Chat Desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1154")
  @Regression
  Scenario: CD :: Agent Desk :: Closed Chat :: Verify calendar picker should be limited to max 90 days back in closed chats
    Given I login as agent of General Bank Demo
    When Agent select "Closed" left menu option
    And  Agent filter by 0 year 3 month and 0 days ago start date and today's end date
    Then Agent checks value of date filter is empty for start date filter 0 year 3 month and 0 days ago
    When Agent opens filter menu
    And Agent checks back button is not visible in calendar for start date filter 91 days ago
    Then Agent checks back button is visible in calendar for end date filter 90 days ago