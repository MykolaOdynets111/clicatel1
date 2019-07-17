@smoke
Feature: Transferring chat

  Verification of basic transfer chat functionality

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-3591")
  Scenario: Verify if agent is able to transfer chat via "Transfer chat" button
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    Given I login as second agent of General Bank Demo
    When First Agent click on new conversation
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming transfer" header
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    And Second agent can see transferring agent name, user name and following user's message: 'connect to agent'
    When Second agent click "Accept transfer" button
    Then Second agent has new conversation request
    And Agent should not see from user chat in agent desk
    When Second agent click on new conversation
    Then Conversation area becomes active with connect to agent user's message in it for second agent
    When Second agent responds with hello to User
    Then User have to receive 'hello' text response for his 'connect to agent' input

