@no_widget
Feature: Agent should be able to see chat history in left menu

  Scenario: Verify agent can filter chat history and view closed chats
    Given Chat history of the client is available for the Agent of General Bank Demo
    Given I login as agent of General Bank Demo
    When Agent select "Chat history" filter option
    When Agent searches and selects chat in chat history list
    Then Agent sees correct chat history
