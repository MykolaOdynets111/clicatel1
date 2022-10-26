@agent_feedback
@Regression
@orca_api
Feature: Create tag

  Background:
    Given Setup ORCA whatsapp integration for Automation tenant
    And agentFeedback tenant feature is set to true for Automation
    Given User select Automation tenant
    Given I open portal
    And Login into portal as an admin of Automation account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Chat Tags page

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2839")
  Scenario: CD:: Supervisor Desk:: Chat_Tags:: Verify when supervisor creates a tag, the newly created tag is available for the agent to use
    Given Create chat tag
    When Agent switches to opened Portal page
    And I select Touch in left menu and Agent Desk in submenu
    When Send connect to support message by ORCA
    And Agent has new conversation request
    When Agent click on new conversation request from touch
    And Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then Agent select precreated tag
    Then Agent click 'Close chat' button