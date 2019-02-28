@no_widget
Feature: Agent should be able to see chat history in left menu

  Scenario: Verify agent can filter chat history and view closed chats
    Given I login as agent of General Bank Demo
    When Agent select "Chat history" filter option
    When Agent searches and selects random chat is chat history list
    Then Get selected chat history from back end
