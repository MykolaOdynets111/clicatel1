@no_widget
@no_chatdesk
@Regression
Feature: Dashboard :: Settings :: Preferences

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1286")
  @setting_changes
  Scenario: CD :: Dashboard :: Configure :: Settings :: Preferences :: Verify if supervisor can switch the toggle on or off for the "Last agent" setting

    Given I login as agent of Automation Bot
    And lastAgentMode tenant feature is set to true for Automation
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    Then Verify if user can change Last Agent Routing status to off
    And Verify if user can change Last Agent Routing status to on

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2637")
  @setting_changes
  Scenario: CD :: Dashboard :: Preferences :: Turning off "Additional Agent Notes to Closed Chats" feature (Bot mode tenant)

    Given Setup ORCA Whatsapp integration for Automation Bot tenant
    And agentFeedback tenant feature is set to false for Automation Bot
    When I login as agent of Automation Bot
    And Send connect to agent message by ORCA
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    And Agent click "End chat" button
    Then End chat popup is not shown for Agent
    And Agent should not see from user chat in agent desk from orca