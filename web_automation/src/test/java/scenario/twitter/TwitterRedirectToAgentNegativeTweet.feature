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
