Feature: Agent should be able to filter chats

  Background:
    Given I login as agent of General Bank Demo
    Given User profile for generalbank is created
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario: Verify agent can filter chats
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent select "Mobile chats" filter option
    Then Agent should not see from user chat in agent desk
    When Agent select "Touch" filter option
    Then Agent has new conversation request
