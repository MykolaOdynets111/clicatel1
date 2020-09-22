@agent_support_hours
@auto_scheduler_disabled
@dot_control
@no_widget
Feature: Supervisor Desk

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    Given Set agent support hours with day shift
    Given autoSchedulingEnabled is set to false
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7394")
  Scenario: Supervisor Desk :: Verify if agent can see and apply each filter (All tickets, Unassigned, Assigned, Processed, Overdue)
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    Then Verify All tickets, Unassigned, Overdue, Assigned ticket types available in dropdown on Inbox
    And User select Unassigned ticket type
    Then Ticket is present on Unassigned filter page
    When autoSchedulingEnabled is set to true
    And Select dot control ticket checkbox
    When Click 'Assign manually' button
    Then 'Assign chat' window is opened
    When I assign chat on Agent
    And User select Assigned ticket type
    Then Ticket is present on Assigned filter page
    And Update ticket with OVERDUE status
    And Wait for 2 second
    When User select OVERDUE ticket type
    Then Ticket is present on OVERDUE filter page