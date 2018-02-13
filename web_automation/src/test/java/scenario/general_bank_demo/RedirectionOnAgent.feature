@agent_to_user_conversation
Feature: user on his demand is redirected on the agent

  Background:
    Given I login as agent of General Bank Demo
    Given User select General Bank Demo
    And Click chat icon

  Scenario: Verify if user is able to communicate with agent:
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with connect to agent user's message in it
    And There is no from agent response added by default for connect to agent user message
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his 'connect to agent' input

