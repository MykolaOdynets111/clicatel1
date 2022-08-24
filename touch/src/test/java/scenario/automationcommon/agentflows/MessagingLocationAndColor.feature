@skip
@no_widget
@dot_control
Feature: Messages location and color

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-1650")
  Scenario: Chat desk: User's and Agent's Messages box are displayed on the correct side in the chat desk and have valid colors
    Given Create .Control integration for Automation Common and adapter: fbmsg
    Given I login as agent of Automation Common
    When Prepare payload for sending Chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    When Send message call
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    Then Conversation area becomes active with Chat to agent user's message
    When Agent responds with hello to User
    Then Messages is correctly displayed and has correct color
