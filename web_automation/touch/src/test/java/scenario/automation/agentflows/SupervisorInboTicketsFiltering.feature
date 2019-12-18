@no_widget
Feature: Supervisor able to filter Tickets

#  Background:
#    Given Create .Control integration for Automation and adapter: fbmsg
#    Given Prepare payload for sending chat to agent message for .Control
#    Given Send parameterized init call with clientId context correct response is returned
#    And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7394")
  Scenario: Supervisor inbox :: Verify if agent can see and apply each filter (All tickets, Unassigned, Assigned, Processed, Overdue)
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Chat console in submenu
    And Select Inbox in Chat console
    When User select Tickets conversation type
    Then Verify All tickets, Assigned, Unassigned, Overdue, Processed ticket types available in dropdown on Inbox
