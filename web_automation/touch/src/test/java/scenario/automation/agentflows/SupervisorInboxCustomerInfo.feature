@no_widget
@dot_control
Feature: Chat history displays right information of the user

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    And Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-15688")
  Scenario: Supervisor desk :: verify that chat history displays right information of the user
    Given I login as agent of Automation
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    And Agent responds with welcome to User
    When Agent switches to opened Portal page
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Supervisor Desk Live has new conversation dotcontrol request
    And Agent click On Live Supervisor Desk chat from dotcontrol channel
    Then Supervisor Desk Live chat container header has dotcontrol User photo, name and fbmsg channel
    And Supervisor Desk Live chat Profile is displayed

