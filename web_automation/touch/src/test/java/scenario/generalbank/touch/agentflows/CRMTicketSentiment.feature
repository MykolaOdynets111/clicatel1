@agent_feedback
@img
Feature: CRM ticket sentiment

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon
    
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4387")
  Scenario: Agent is able to see and select sentiment for CRM ticket
    Given AGENT_FEEDBACK tenant feature is set to true for General Bank Demo
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then Agent can see default Supply a note... placeholder for note if there is no input made
    Then Agent can see valid sentiments (Neutral sentiment by default, There are 3 icons for sentiments)
    Then Agent is able to select sentiments, when sentiment is selected, 2 other should be blurred


