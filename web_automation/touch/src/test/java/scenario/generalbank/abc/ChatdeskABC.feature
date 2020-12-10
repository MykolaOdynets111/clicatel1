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
    Then Valid image for apple integration are shown in left menu with chat
#    And Agent should see apple chat icon in header


