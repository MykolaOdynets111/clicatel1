@no_widget
  @Regression
Feature: Agent should be able to see chat history in left menu

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2857")
  Scenario: Verify agent can filter closed chats and chat history with channel icon of the channel that chat has taken place
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
    Then Valid image for whatsapp integration are shown in left menu with chat
    Then Agent sees correct chat history