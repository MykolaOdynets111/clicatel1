@agent_to_user_conversation
@twitter
Feature: Redirection to the agent after negative DM message

  Background:
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open direct message channel

  Scenario: Twitter: Redirecting to the agent negative direct message
    When User sends twitter direct message: Hate your banking!! Will never use it again
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with Hate your banking!! Will never use it again message from twitter user
