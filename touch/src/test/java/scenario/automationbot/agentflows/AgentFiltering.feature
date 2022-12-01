@Regression
@no_widget
Feature: Filtering : Chat Desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1154")
  Scenario: CD :: Agent Desk :: Closed Chat :: Verify calendar picker should be limited to max 90 days back in closed chats
    Given I login as agent of General Bank Demo
    When Agent select "Closed" left menu option
    And  Agent filter by 0 year 3 month and 0 days ago start date and today's end date
    Then Agent checks value of date filter is empty for start date filter 0 year 3 month and 0 days ago
    When Agent opens filter menu
    And Agent checks back button is not visible in calendar for start date filter 91 days ago
    Then Agent checks back button is visible in calendar for end date filter 90 days ago

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1470")
  Scenario: CD :: Agent Desk :: Live Chat :: Flag Chat :: Verify if agent can filter "flagged chats"
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send 2 messages Flagged chat filter by ORCA
    And Agent has new conversation request
    And Agent click on new conversation request from orca
    And Agent sees Correct number of filtered chats shown as 2
    And Agent click 'Flag chat' button
    And Agent filter closed chats with no channel, no sentiment and flagged is true
    Then Agent sees Correct number of filtered chats shown as 1
    When Agent click 'Unflag chat' button
    And Agent receives an error message "Sorry, no results found. Please refine your search and try again"