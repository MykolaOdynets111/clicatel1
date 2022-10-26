@Regression
@no_widget
Feature: Profanity filter on agent's messages

  Background:
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send The app needs a username for activation message by ORCA

  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2407")
  Scenario: CD:: Agent Desk:: Verify if profanity filter is applied to agent's messages
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    Then Conversation area becomes active with The app needs a username for activation user's message
    When Agent responds with fuck off to User
    Then 'Profanity not allowed' pop up is shown
    And Agent closes 'Profanity not allowed' popup
    And Agent clear input and send a new message how can I help you?
    And Verify Orca returns how can I help you? response during 40 seconds

  @TestCaseId("https://jira.clickatell.com/browse/CCD-5076")
  Scenario: CD:: Agent Desk:: Profanity restriction message is not appearing for normal chats for WA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    Then Conversation area becomes active with The app needs a username for activation user's message
    And Agent responds with non profanity message to User
    And 'Profanity not allowed' pop up is not shown