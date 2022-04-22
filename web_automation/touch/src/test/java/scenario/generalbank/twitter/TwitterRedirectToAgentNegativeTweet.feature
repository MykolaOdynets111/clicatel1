@agent_to_user_conversation
@twitter
@skip
Feature: Redirection to the agent after negative tweet

  Background:
    Given Login to twitter
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open new tweet window

  Scenario: Twitter: Redirecting to the agent negative tweet
    When User sends tweet regarding "Hate your banking"
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with Hate your banking user's message
