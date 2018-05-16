@agent_to_user_conversation
@twitter
Feature: User should be redirected to the agent when sends negative message into DM

  Background:
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open direct message channel

  Scenario: Redirecting to the agent negative negative direct message
    When User sends twitter direct message "Hate your banking"
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with Hate your banking user's message