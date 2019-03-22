Feature: Agent should be able to filter chats

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  Scenario: Verify agent can filter chats
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent select "Twitter" filter option
    Then Agent should not see from user chat in agent desk
    When Agent select "Webchat" filter option
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to agent user's message
