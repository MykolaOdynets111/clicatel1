@no_widget
Feature: Agent should be able to see chat history in left menu

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4682")
  Scenario: Verify agent can filter closed chats and chat history with channel icon of the channel that chat has taken place
    Given Chat history of the client is available for the Agent of General Bank Demo
    Given I login as agent of General Bank Demo
    When Agent select "Chat history" filter option
    When Agent searches and selects chat in chat history list
    Then Valid image for touch integration are shown in left menu with chat
    Then Agent sees correct chat history
