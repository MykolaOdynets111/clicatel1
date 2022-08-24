Feature: Tenant with Starter plan should be able to have only 1 logged in agent

  Background:
    Given New tenant is successfully created
    Given Second agent of SignedUp AQA is successfully created
    Given I login as agent of SignedUp AQA
    Given User select SignedUp AQA tenant
    And Click chat icon

  Scenario: Agent seats limitation for Starter plan
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to agent user's message
    When Try to login as second agent of SignedUp AQA
    Then Agent limit reached popup is show for second agent
    When Agent replays with Hi, how can I help you? message
    Then User should see 'Hi, how can I help you?' text response for his 'connect to agent' input

