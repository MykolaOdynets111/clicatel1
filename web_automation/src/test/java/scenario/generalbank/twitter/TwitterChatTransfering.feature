@agent_to_user_conversation
@twitter
Feature: Twitter chat transferring

  Background:
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open direct message channel

  Scenario: Twitter: Verify if agent is able to transfer twitter chat via "Transfer chat" button
    When User sends twitter direct message regarding chat to support
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Given I login as second agent of General Bank Demo
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming transfer" header
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    And Second agent can see transferring agent name, twitter user name and following user's message: 'connect to agent'
    When Second agent click "Accept transfer" button
    Then Second agent has new conversation request from twitter user
    And Agent should not see from user chat in agent desk
    When Second agent click on new conversation request from twitter
    Then Conversation area becomes active with connect to support user's message in it for second agent
    When Second agent responds with hello to User
    Then User have to receive correct response "hello" on his message "chat to support"