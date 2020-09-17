@no_widget
@dot_control
Feature: Supervisor desk

  Background:
    Given Create .Control integration for Automation Bot and adapter: fbmsg
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-7404")
  Scenario: supervisor desk:: verify if correct name of agent is shown in supervisor desk
    Given I login as second agent of Automation Bot
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    Then Second agent has new conversation request from dotcontrol user
    When Second agent click on new conversation request from dotcontrol
    And Second agent responds with hello to User
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Supervisor Desk Live has new conversation dotcontrol request
    And Live chats Second agent filter has correct name and correct chats number
