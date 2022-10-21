@no_widget
@Regression
Feature: Apple Business Chat :: Chatdesk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-3001")
  @orca_api
  Scenario: CD :: Agent Desk :: Live Chat :: ABC :: Verify that chat header should have apple icon when user is chatting using apple chat

    Given Setup ORCA abc integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    And Conversation area becomes active with connect to Support user's message
    Then Agent should see apple-business-chat integration icon in left menu with chat
    And Agent should see apple-business-chat icon in active chat header

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2328")
  @orca_api
  Scenario: CD:: ABC:: Agent Desk:: //END message works for apple business chat
    Given I login as agent of General Bank Demo
    Given Setup ORCA abc integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message


  @orca_api
  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1618")
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer Chat :: Verify if agent is able to transfer Apple business chat via "Transfer chat" button (ABC)
    Given I login as agent of General Bank Demo
    Given Setup ORCA abc integration for General Bank Demo tenant
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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2856")
  Scenario: Chatdesk:: Verify if agent can filter closed chat using Apple business chat channel
    Given I login as agent of General Bank Demo
    When Agent select "Closed" left menu option
    When Agent filter closed chats with Apple Business Chat channel, no sentiment and flagged is false
    Then Agent see only apple_business_chat chats in left menu