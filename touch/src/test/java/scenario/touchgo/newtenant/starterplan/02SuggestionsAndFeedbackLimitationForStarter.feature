Feature: Agent Assist and Feedback features for tenant with Starter plan

  Agent assist (suggestions) should be turned off by default
  Agent feedback  (chat conclusion) should be turned on by default

  Background:
    Given New tenant is successfully created
    Given I login as agent of SignedUp AQA
    Given User select SignedUp AQA tenant
    And Click chat icon

  Scenario: Agent assist and Feedback should be turned off for Starter
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to agent user's message
    When User enter how to check account balance? into widget input field
    Then Conversation area contains how to check account balance? user's message
    And Suggestions are not shown
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his 'how to check account balance?' input
    When Agent click "End chat" button
    Then Agent Feedback popup for agent should be opened
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
