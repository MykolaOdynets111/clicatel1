@agent_to_user_conversation
@twitter
Feature: Communication with agent via tweet

  Background:
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open new tweet window

  Scenario: Receiving answer on tweet from the agent
    When User sends tweet regarding "connect to agent"
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with connect to agent user's message
    When Agent replays with how can i help you? message
    Then Agent's answer arrives to twitter
    And User has to receive "how can i help you?" answer from the agent as a comment on his initial tweet connect to agent
    And Send "where can I find currency exchange rate?" reply on agent's tweet "how can i help you?"
    Then Conversation area contains where can I find currency exchange rate? user's message
    When Agent replays with please check out our web site message
    Then User have to receive please check out our web site agent response as comment for where can I find currency exchange rate? tweet

