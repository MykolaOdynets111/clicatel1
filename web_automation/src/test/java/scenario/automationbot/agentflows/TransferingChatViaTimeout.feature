@inactivity_timeout
Feature: Transferring chat after timeout

  Verification of automatic transfer chat functionality

  Background:
    Given Transfer timeout for Automation Bot tenant is set to 5 seconds
    Given User select Automation Bot tenant
    Given I login as agent of Automation Bot
    And Click chat icon

  Scenario: Verify if chat is automatically transferred after agentIncativity timeout
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    Given I login as second agent of Automation Bot
    Then Second agent has new conversation request within 50 seconds
    And First agent should not see from user chat in agent desk
    When Second agent click on new conversation
    Then Conversation area becomes active with connect to agent user's message in it for second agent
    When Second agent responds with hello to User
    Then User have to receive 'hello' text response for his 'connect to agent' input

