@agent_to_user_conversation
@facebook
Feature: Agent availability in terms of fb direct messages

  Background:
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page

  Scenario: Agent availability for fb user
    When User opens Messenger and send message regarding connect to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with connect to agent message from facebook user
    When Agent responds with hello to User
    Then User have to receive the following on his message regarding connect to agent: "hello"
    When Agent closes chat
    Then User have to receive the following on his message regarding chat to agent: "hello"
    When Agent changes status to: Unavailable
    When User sends message regarding connect to support
    Then User have to receive the following on his message regarding chat to agent: "agents_away"
    Then Agent should not see from user chat in agent desk
    When Agent changes status to: Available
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    When Agent responds with How can I help you? to User
    Then User have to receive the following on his message regarding connect to agent: "How can I help you?"




