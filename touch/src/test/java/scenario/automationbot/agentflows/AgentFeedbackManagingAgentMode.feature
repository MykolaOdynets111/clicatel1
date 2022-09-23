@agent_feedback
Feature: Turning on / off AGENT_FEEDBACK feature for Agent mode tenant

  Background:
    Given Setup ORCA whatsapp integration for Automation Bot tenant

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2975")
  @Regression
  Scenario: CD:: Dashboard Setting:: Turning off 'Additional Agent Notes to Closed Chat' feature (Agent mode tenant)
    Given agentFeedback tenant feature is set to false for Automation Bot
    And I login as agent of Automation Bot
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When Agent click "End chat" button
    Then End chat popup is not shown
    And Agent should not see from user chat in agent desk from orca

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2864")
  Scenario: CD:: Dashboard Setting:: Turning on 'Additional Agent Notes to Closed Chat' feature (Bot mode tenant)
    Given agentFeedback tenant feature is set to true for Automation Bot
    And I login as agent of Automation Bot
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When Agent click "End chat" button
    Then Agent Feedback popup for agent should be opened
    And Agent click 'Close chat' button
    And Agent should not see from user chat in agent desk