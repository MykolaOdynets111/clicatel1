@agent_to_user_conversation
@facebook
Feature: Redirection to the agent negative DM fb message

  Background:
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page

  Scenario: Redirection to the agent negative DM fb message
    When User opens Messenger and send message regarding Your service is awful!
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with Your service is awful! message from facebook user


