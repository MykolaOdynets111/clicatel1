@support_hours
@setting_changes
@dot_control
@no_widget
Feature: Supervisor desk

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    Given Set agent support hours with day shift
    Given Turn off tickets autoScheduling
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7394")
  Scenario: Supervisor Desk :: Verify if agent can see and apply each filter (All tickets, Unassigned, Assigned, Processed, Overdue)
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    Then Verify All tickets, Unassigned, Expired, Assigned ticket types are available
    And User select Unassigned ticket type
    Then Ticket from dotcontrol is present on Unassigned filter page
    When Turn on tickets autoScheduling
    And Select dotcontrol ticket checkbox
    When Click 'Assign manually' button
    Then 'Assign chat' window is opened
    When I assign chat on Agent
    And User select Assigned ticket type
    Then Ticket from dotcontrol is present on Assigned filter page
    And Update ticket with OVERDUE status
    And Wait for 2 second
    When User select Expired ticket type
    Then Ticket from dotcontrol is present on Expired filter page