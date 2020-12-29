@no_widget
@agent_support_hours
@auto_scheduler_disabled
@dot_control
Feature: Supervisor desk

   Background:
     Given Create .Control integration for Automation Bot and adapter: fbmsg
     Given Set agent support hours with day shift
     Given autoSchedulingEnabled is set to false
     Given Prepare payload for sending chat to agent message for .Control
     Given Send parameterized init call with clientId context correct response is returned
     And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7391")
  @Issue("https://jira.clickatell.com/browse/TPLAT-4836")
  Scenario: Supervisor desk:: Route ticket to scheduler
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    When I login as second agent of Automation Bot
    And Select dotcontrol ticket checkbox
    When Click 'Route to scheduler' button
    And Second agent select "Tickets" left menu option
    Then Second agent has new conversation request from dotcontrol user
    And Second agent is set as 'current agent' for dot control ticket
