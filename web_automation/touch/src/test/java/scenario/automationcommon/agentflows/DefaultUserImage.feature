@skip
@no_widget
@dot_control
Feature: Default user profile icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-1649")
  Scenario: If user doesn`t have profile picture, default picture should be shown(web widget)
    Given Create .Control integration for Automation Common and adapter: fbmsg
    Given I login as agent of Automation Common
    When Prepare payload for sending Chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    When Send message call
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    Then Conversation area becomes active with Chat to agent user's message
    Then Default user image is shown




