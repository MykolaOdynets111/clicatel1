@agent_to_user_conversation
@facebook
Feature: Communication between user and agent in FB messenger

  Background:
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page

  Scenario: Communication between user and bot
    When User opens Messenger and send message regarding chat to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with chat to support message from facebook user
    When Agent responds with hello to User
    Then User have to receive the following on his message regarding chat to support: "hello"
    When User sends message regarding can i open saving accounts?
    Then Conversation area contains can i open saving accounts? message from facebook user

