@no_widget
Feature: Whatsapp ORCA :: Chatdesk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118502")
  Scenario: ChatDesk:: The header should have whatsapp icon when user is chatting using orca whatsapp
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    Then Valid image for whatsapp integration are shown in left menu with chat
    And Agent should see whatsappHeader icon in active chat header

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118503")
  Scenario: ChatDesk: ORCA WhatsApp: Verify if //END message works for whatsapp chat
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Send //end message by ORCA
    Then Agent should not see from user chat in agent desk from orca

  @orca_api
  @start_orca_server
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118504")
  Scenario: ChatDesk:: Verify if agent is able to transfer Orca WhatsApp chat via "Transfer chat" button
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
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

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118505")
  Scenario: ChatDesk:: Verify if agent can filter closed chat using WhatsApp chat channel
    Given I login as agent of General Bank Demo
    When Agent select "Closed" left menu option
    When Agent filter closed chats with WhatsApp channel, no sentiment and flagged is false
    Then Agent see only whatsapp chats in left menu

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118468")
  Scenario: CD :: Agent Desk :: Live Chat :: Location :: Verify if agent click on the small cross on search bar, the text entered in the search bar is deleted
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When Agent open chat location form and set Toronto Location and click cancel button
    Then Location field becomes empty