@no_widget
@agent_support_hours
@auto_scheduler_disabled
@dot_control
Feature: Supervisor in box : route ticket to scheduler

   Background:
     Given Create .Control integration for Automation Bot and adapter: fbmsg
     Given Set agent support hours with day shift
     Given autoSchedulingEnabled is set to false
     Given Prepare payload for sending chat to agent message for .Control
     Given Send parameterized init call with clientId context correct response is returned
     And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7391")
  @Issue("https://jira.clickatell.com/browse/TPLAT-4836")
  Scenario: Supervisor inbox :: Route ticket to scheduler
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Chat console in submenu
    When Click three dots for dot control ticket
    And Click 'Route to scheduler' button
    When I login as second agent of Automation Bot
    And Second agent select "Tickets" left menu option
    Then Second agent has new conversation request from dotcontrol user
    And Second agent is set as 'current agent' for dot control ticket


