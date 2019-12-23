@no_widget
@dot_control
Feature: Supervisor able to check live chats

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-15691")
  Scenario: Supervisor inbox :: verify that supervisor able to check live chats
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Chat console in submenu
    And Select Inbox in Chat console
    When User select Live chats conversation type
    Then Verify that live chat is displayed with chat to agent message to agent
    And I launch chatdesk from portal
    Then Agent has new conversation request from dotcontrol user

