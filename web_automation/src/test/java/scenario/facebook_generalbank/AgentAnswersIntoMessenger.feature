@agent_to_user_conversation
@facebook
Feature: Communication between user and agent in FB messenger

  Scenario: Communication between user and bot
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page
    When Open Messenger and send message regarding chat to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with chat to support message from facebook user
