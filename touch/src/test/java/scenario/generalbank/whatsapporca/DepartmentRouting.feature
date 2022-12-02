@remove_dep
@start_orca_server
@Regression
@setting_changes
Feature: Departments: Route to Department

  Background:
    Given I open portal
    When Login into portal as an admin of General Bank Demo account
    And New departments with AutomationFirstAgent name AutomationFirstAgentDescription description and first agent is created
    And New departments with AutomationSecondAgent name AutomationSecondAgent description and second agent is created
    When Setup ORCA whatsapp integration for General Bank Demo tenant
    And lastAgentMode tenant feature is set to false for General Bank Demo
    And Turn off the Default department

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1857")
  @orca_api
  Scenario: CD :: Dashboard :: Department Mgmt. :: Verify that chats is should be connected to department selected by customer when "route to specific" department is turned on
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Preferences page
    And Select AutomationFirstAgent department By Default
    When Agent switches to opened Portal page
    And I select Touch in left menu and Agent Desk in submenu
    When Send to agent message by ORCA to AutomationSecondAgent department
    Then Verify Orca returns Agent Busy message autoresponder during 40 seconds for agent
    Given I login as second agent of General Bank Demo
    Then Second agent has new conversation request from orca user

