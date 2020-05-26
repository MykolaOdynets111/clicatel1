@agent_support_hours
@auto_scheduler_disabled
@dot_control
@no_widget
Feature: Supervisor able to filter Tickets

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    Given Set agent support hours with day shift
    Given autoSchedulingEnabled is set to false
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7394")
  Scenario: Supervisor inbox :: Verify if agent can see and apply each filter (All tickets, Unassigned, Assigned, Processed, Overdue)
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Chat console in submenu
    When User select Tickets conversation type
    Then Verify All tickets, Assigned, Unassigned, Overdue, Processed ticket types available in dropdown on Inbox
    And User select Unassigned ticket type
    Then Ticket is present and has Unassigned type
    When autoSchedulingEnabled is set to true
    And Click three dots for dot control ticket
    When Click 'Assign manually' button
    Then 'Assign chat' window is opened
    When I assign chat on Agent
    And User select Assigned ticket type
    Then Ticket is present and has Assigned type
    And Update ticket with PROCESSED status
    When User select PROCESSED ticket type
    Then Ticket is present and has PROCESSED type
    And Update ticket with OVERDUE status
    When User select OVERDUE ticket type
    Then Ticket is present and has OVERDUE type