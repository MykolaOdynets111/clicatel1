@agent_to_user_conversation
@facebook
@fb_dm
Feature: Facebook chat transferring

  Background:
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page

  Scenario: Verify if agent is able to transfer facebook chat via "Transfer chat" button
    When User opens Messenger and send message regarding chat to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with chat to agent message from facebook user
    Given I login as second agent of General Bank Demo
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    And Second agent can see transferring agent name, facebook user name and following user's message: 'connect to agent'
    When Second agent click "Accept transfer" button
    Then Agent should not see from user chat in agent desk
    And Second agent has new conversation request from facebook user
    When Second agent click on new conversation request from facebook
    Then Conversation area becomes active with chat to agent user's message in it for second agent
    When Second agent responds with hello to User
    Then User have to receive the following on his message regarding chat to agent: "hello"
