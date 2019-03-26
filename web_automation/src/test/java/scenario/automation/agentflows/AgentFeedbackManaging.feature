@agent_feedback
Feature: Turning off AGENT_FEEDBACK feature for Agent mode tenant

  Background:
    Given User select Automation tenant
    And Click chat icon

  Scenario: Turning AGENT_FEEDBACK feature
    Given AGENT_FEEDBACK tenant feature is set to false for Automation
    And I login as agent of Automation
    When User enter Chat to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    Then Agent Feedback popup is not shown

