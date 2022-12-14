@Regression
@no_widget
Feature: Filtering : Chat Desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1154")
  Scenario: CD :: Agent Desk :: Closed Chat :: Verify calendar picker should be limited to max 90 days back in closed chats
    Given I login as agent of General Bank Demo
    When Agent select "Closed" left menu option
    When Agent opens filter menu
    Then Agent cannot select start date more than 90 days ago in supervisor
    And Agent cannot select end date more than 90 days ago in supervisor

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