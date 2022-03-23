@no_widget
Feature: Apple Business Chat :: Chatdesk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45496")
  Scenario: Chatdesk:: The header should have apple icon when user is chatting using apple chat
    Given I login as agent of General Bank Demo
    Given Setup ORCA integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    Then Valid image for abc integration are shown in left menu with chat
    And Agent should see abcHeader icon in active chat header

  @orca_api
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

  @orca_api
  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45512")
  Scenario: Chatdesk:: Verify if agent is able to transfer Apple business chat via "Transfer chat" button
    Given I login as agent of General Bank Demo
    Given Setup ORCA integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When I login as second agent of General Bank Demo
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    And Second agent can see transferring agent name, orca and following user's message: 'connect to agent'
    When Second agent click "Accept transfer" button
    Then Second agent has new conversation request from orca user
    When Second agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message in it for second agent
    When Second agent responds with hello to User
    Then Verify Orca returns hello response during 40 seconds

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45510")
  Scenario: Chatdesk:: Verify if agent can filter closed chat using Apple business chat channel
    Given I login as agent of General Bank Demo
    When Agent select "Closed" left menu option
    When Agent filter closed chats with Apple Business Chat channel, no sentiment and flagged is false
    Then Agent see only apple_business_chat chats in left menu