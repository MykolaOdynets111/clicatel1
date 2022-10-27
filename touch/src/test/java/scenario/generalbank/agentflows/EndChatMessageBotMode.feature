@camunda
@setting_changes
@no_widget
@orca_api
Feature: End chat flow

  Background:
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And agentFeedback tenant feature is set to true for General Bank Demo

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2990")
  @Regression
  Scenario: CD :: Agent Desk :: Close Chat :: Verify that close chat message enabling and editing for Bot mode tenant
    Given Taf End Chat message is set to true for General Bank Demo tenant
    And Taf End Chat message message text is updated for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk from orca