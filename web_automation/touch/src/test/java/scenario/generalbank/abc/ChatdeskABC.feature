@no_widget
@orca_api
Feature: Apple Business Chat :: Chatdesk

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45496")
  Scenario: Chatdesk:: The header should have apple icon when user is chatting using apple chat
    Given I login as agent of General Bank Demo
    Given Setup ORCA integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    Then Valid image for abc integration are shown in left menu with chat
    And Agent should see abc icon in active chat header


  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45561")
  Scenario: Chat desk: ABC: Verify if //END message works for apple business chat
    Given I login as agent of General Bank Demo
    Given Setup ORCA integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Send //end message by ORCA
    Then Agent should not see from user chat in agent desk from orca
