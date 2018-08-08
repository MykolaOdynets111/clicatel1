@portal
Feature: Newly created agent should be able to login into to chat desk

  Background:
    Given User profile for generalbank is created
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario: Newly created agent should be able to login into to chat desk
    Given New General Bank Demo agent is created
    When I open portal
    And Login as newly created agent
    Then Agent is logged in chat desk
    When User enter Chat to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with Chat to Support user's message
    When Agent responds with Hello, how can I help you? to User
    Then User have to receive 'Hello, how can I help you?' text response for his 'Chat to Support' input