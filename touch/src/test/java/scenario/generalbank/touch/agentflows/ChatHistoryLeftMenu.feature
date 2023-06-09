@no_widget
  @Regression
@orca_api
Feature: Agent should be able to see chat history in left menu

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2901")
  Scenario: CD :: Agent Desk :: Closed Chat :: Verify that closed chats moved to chat history section
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Agent select "Closed" left menu option
    And Agent filter closed chats with WhatsApp channel, no sentiment and flagged is false
    When Agent opens first chat from the list
    And Agent gets initial chat messages for chat history
    #Then Valid image for whatsapp integration are shown in left menu with chat
    Then Agent sees correct chat history

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2857")
  Scenario: CD:: Agent Desk:: Verify agent can filter closed chats and chat history with channel icon of the channel that chat has taken place
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    #And Chat history of the client is available for whatsapp channel, the Agent of General Bank Demo
    When Send connect to agent message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    When Agent select "Closed" left menu option
    When Agent searches and selects chat from orca in chat history list

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1872")
  Scenario: CD:: SMS:: History Tab:: Verify that once chat from chat history has loaded Agent is able to view entire history of the chat conversation
    Given I login as agent of General Bank Demo
    When Setup ORCA sms integration for General Bank Demo tenant
    And Send connect to agent 1 message by ORCA with same number
    And Agent has new conversation request from sms user
    And Agent click on new conversation request from sms
    And Agent gets initial chat messages for chat history
    And Agent closes chat
    And Send connect to agent 2 message by ORCA with same number
    And Agent has new conversation request from sms user
    And Agent click on new conversation request from sms
    And Conversation area becomes active with connect to agent 2 user's message
    And Agent searches and selects chat from sms in chat history list
    Then Agent sees correct chat history