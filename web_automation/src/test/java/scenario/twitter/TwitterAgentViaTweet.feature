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
    When Agent replays with how can i help you? message
    Then User has to receive "how can i help you?" answer from the agent
    When He clicks "how can i help you?" tweet
    And Send "where can I find currency exchange rate?" reply into tweet
    Then Conversation area contains where can I find currency exchange rate? user's message
    When Agent replays with please check out our web site message
    Then User have to receive please check out our web site agent response as comment for how can i help you? tweet

