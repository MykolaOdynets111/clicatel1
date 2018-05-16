@agent_to_user_conversation
@twitter
Feature: User be redirected to the agent after sending negative tweet

  Background:
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open new tweet window

  Scenario: Redirecting to the agent negative tweet
    When User sends tweet regarding "Hate your banking"
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with Hate your banking user's message
#    When Agent replays with how can i help you? message
#    Then User has to receive "how can i help you?" answer from the agent
#    When He clicks "how can i help you?" tweet
#    And Send "where can I find currency exchange rate?" reply into tweet
#    Then Conversation area contains where can I find currency exchange rate? user's message
#    When Agent replays with please check out our web site message
#    Then User have to receive please check out our web site agent response as comment for how can i help you? tweet

