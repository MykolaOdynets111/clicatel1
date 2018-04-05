@agent_to_user_conversation
@facebook
Feature: Communication between user and agent in FB messenger

  Scenario: Communication between user and bot
    Given Open General Bank Demo page
    Given I login as agent of General Bank Demo
    When Open Messenger and send chat to support message
    Then Agent has new conversation request from FB user
