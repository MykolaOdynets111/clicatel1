@agent_to_user_conversation
@facebook
@fb_dm
Feature: Communication between user and agent in FB messenger

  Background:
    Given Login to fb
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page

  Scenario: Facebook: Communication between user and agent in FB messenger
    When User opens Messenger and send message regarding chat to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with chat to agent message from facebook user
    When Agent responds with hello to User
    Then User have to receive the following on his message regarding chat to agent: "hello"
    When User sends message regarding how to check account balance
    Then Conversation area contains how to check account balance message from facebook user
    When Agent responds with sure. please provide us with your telephone number and we will contact you to User
    Then User have to receive the following on his message regarding how to check account balance: "sure. please provide us with your telephone number and we will contact you"

