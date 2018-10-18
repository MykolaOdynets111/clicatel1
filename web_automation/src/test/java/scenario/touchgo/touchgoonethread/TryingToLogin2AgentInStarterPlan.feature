Feature: Tenant with Starter plan should be able to have only 1 logged in agent

  Background:
    Given I login as agent of Starter AQA
    Given User select Starter AQA tenant
    And Click chat icon

  Scenario: Admin should be able to open chat desk from portal
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to agent user's message
    When Try to login as second agent of Starter AQA
    Then Agent limit reached popup is show for second agent
    When Agent replays with Hi, how can I help you? message
    Then User have to receive 'Hi, how can I help you?' text response for his 'connect to agent' input

