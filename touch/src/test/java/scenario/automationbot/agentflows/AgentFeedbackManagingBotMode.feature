@agent_feedback
Feature: Turning on / off AGENT_FEEDBACK feature for Bot mode tenant

  Background:
    Given User select Automation Bot tenant
    And Click chat icon

  Scenario: Turning on 'Additional Agent Notes to Closed Chat' feature (Bot mode tenant)
    Given AGENT_FEEDBACK tenant feature is set to true for Automation Bot
    And I login as agent of Automation Bot
    When User enter Chat to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent click "End chat" button
    Then Agent Feedback popup for agent should be opened
    And Agent click 'Close chat' button
    And Agent should not see from user chat in agent desk