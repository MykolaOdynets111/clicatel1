@agent_mode
Feature: User messages handling in "Agent" tenant mode

  Verification of basic communication between user and agent


  Scenario: Verify if user is redirected to the agent after NEUTRAL or POSITIVE message
    Given I login as agent of Automation
    Given User select Automation tenant
    And Click chat icon
    When User enter account balance into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with account balance user's message
    And There is no more than one from user message
    And There is no from agent response added by default for account balance user message
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his 'account balance' input

  @suggestions
  Scenario: Verify if suggestion are not shown in "Agent" mode
    Given AGENT_ASSISTANT tenant feature is set to true for Automation
    Given I login as agent of Automation
    Given User select Automation tenant
    And Click chat icon
    When User enter what is your open hours into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with what is your open hours user's message
    And There is no suggestions on 'what is your open hours' user input
    When Agent responds with thanks for asking to User
    Then User have to receive 'thanks for asking' text response for his 'what is your open hours' input




