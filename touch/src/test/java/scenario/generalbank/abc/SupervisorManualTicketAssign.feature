@Regression
@support_hours
Feature: Supervisor desk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2846")
  Scenario: CD::Supervisor desk:: Supervisor_Desk-Chat:: Supervisor_Desk-Tickets-All_Tickets:: Assign ticket manually to agent
    Given Setup ORCA abc integration for General Bank Demo tenant
    And Set agent support hours with day shift
    When Send Manual Ticket Assignment message by ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    Then Agent see tickets from orca on Unassigned filter page
    And Select orca ticket checkbox
    When Click 'Assign manually' button for orca
    Then 'Assign chat' window is opened
    When I assign chat on second agent for Agent dropdown
    And Agent select Assigned filter on Left Panel
    And Admin refreshes the page
    And Agent search chat orca on Supervisor desk
    Then GBD Second is the current agent of orca ticket
    And I login as second agent of General Bank Demo
    And Second agent select "Tickets" left menu option
    And Second agent select Assigned filter on Left Panel
    Then Agent see tickets from orca on Assigned filter page
