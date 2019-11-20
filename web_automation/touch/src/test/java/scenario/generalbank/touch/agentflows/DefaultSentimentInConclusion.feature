@no_widget
@img
Feature: Default sentiment during conclusion

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4002")
  Scenario: Conclusion window :: Verify that neutral sentiment is set by default
    Given Create .Control integration for General Bank Demo and adapter: fbmsg
    Given I login as agent of General Bank Demo
    When Prepare payload for sending Chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    When Send message call
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    Then Conversation area becomes active with Chat to agent user's message
    When Agent click "End chat" button
    Then Correct neutral sentiment selected
