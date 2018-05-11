@agent_to_user_conversation
@twitter
Feature: User should be able to receive answer on his tweet from the agent

  Background:
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open new tweet window

  Scenario: Receiving answer on tweet from the agent
    When User sends tweet regarding "chat to support"
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with chat to support user's message
    And There is no from agent response added by default for chat to support user message
    When Agent replays with please have a look on our website message
    Then User has to receive "please have a look on our website" answer
    When He clicks "please have a look on our website" tweet