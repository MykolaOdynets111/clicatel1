@agent_to_user_conversation
@facebook
@fb_dm
Feature: Communication between user and agent in FB messenger

  Background:
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page

  Scenario: Communication between user and agent
    When User opens Messenger and send message regarding chat to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with chat to agent message from facebook user
    And There is no from agent response added by default for chat to agent message from fb user
    When Agent responds with hello to User
    Then User have to receive the following on his message regarding chat to agent: "hello"
    When User sends message regarding can i open saving accounts?
    Then Conversation area contains can i open saving accounts? message from facebook user
    When Agent responds with sure. please provide us with your telephone number and we will contact you to User
    Then User have to receive the following on his message regarding can i open saving accounts?: "sure. please provide us with your telephone number and we will contact you"

