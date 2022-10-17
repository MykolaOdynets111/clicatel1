@Regression
@no_widget
Feature: Profanity filter on agent's messages

  Background:
    Given Setup ORCA whatsapp integration for Automation Bot tenant
    And I login as agent of Automation Bot
    When Send The app needs a username for activation message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2407")
  Scenario: CD:: Agent Desk:: Verify if profanity filter is applied to agent's messages
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    Then Conversation area becomes active with chat to agent user's message
    When Agent responds with fuck off to User
    Then 'Profanity not allowed' pop up is shown
    When Agent closes 'Profanity not allowed' popup
    When Agent clear input and send a new message how can I help you?
    Then Conversation area becomes active with how can I help you? user's message

  @TestCaseId("https://jira.clickatell.com/browse/CCD-5076")
  Scenario: CD:: Agent Desk:: Profanity restriction message is not appearing for normal chats for WA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    Then Conversation area becomes active with chat to agent user's message
    When Agent responds with non profanity message to User
    Then 'Profanity not allowed' pop up is not shown