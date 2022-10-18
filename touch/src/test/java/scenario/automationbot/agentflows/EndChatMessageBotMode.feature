@camunda
@setting_changes
@no_widget
Feature: End chat flow: bot mode

  Background:
    Given Setup ORCA whatsapp integration for Automation Bot tenant
    And agentFeedback tenant feature is set to true for Automation Bot

  Scenario: End chat message disabling for Bot mode tenant
    Given Taf start_new_conversation is set to false for Automation Bot tenant
    Given User select Automation Bot tenant
    And Click chat icon
    Given I login as agent of Automation Bot
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then There is no exit response

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2990")
  @Regression
  Scenario: CD :: Agent Desk :: Close Chat :: Verify that close chat message enabling and editing for Bot mode tenant
    Given Taf End Chat message is set to true for Automation Bot tenant
    And Taf End Chat message message text is updated for Automation Bot tenant
    And I login as agent of Automation Bot
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk from orca

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2750")
  @Regression
  Scenario: CD :: Dashboard :: Settings :: End chat message resetting for Bot mode tenant
    Given Taf End Chat message is set to true for Automation Bot tenant
    And Taf End Chat message message text is updated for Automation Bot tenant
    And I login as agent of Automation Bot
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Auto Responders page
    When Wait for auto responders page to load
    And Agent click expand arrow for End chat message auto responder
    And Click "Reset to default" button for End chat message auto responder
    Then End Chat message is reset on backend