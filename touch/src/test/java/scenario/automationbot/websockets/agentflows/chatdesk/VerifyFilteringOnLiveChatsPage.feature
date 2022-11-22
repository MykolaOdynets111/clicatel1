@Regression
@no_widget
Feature: Filtering : Chat Desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2867")
  @start_orca_server
  @orca_api
  Scenario: CD :: Agent Desk :: Live Chat :: Verify if clicking on new chat message will open chat screen

    Given Setup ORCA Whatsapp integration for Automation Bot tenant
    And I login as agent of Automation Bot
    When Send 1 messages chat to agent by ORCA
    Then Agent has new conversation request
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with chat to agent user's message
