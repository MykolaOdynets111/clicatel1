@no_widget
@dot_control
Feature: Chat history displays right information of the user

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    And Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call
    Given Set Lviv as customer location with api

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-15688")
  Scenario: Supervisor inbox :: verify that chat history displays right information of the user
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Chat console in submenu
    Then Verify that New status is shown for inbox conversation
    And Verify correct information is shown in Customer details and Lviv set as location
    When I launch chatdesk from portal
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    And Agent click "End chat" button
    When Agent click 'Close chat' button
    And Agent switches to opened Portal page
    And Agent refresh current page
    Then Verify that Closed status is shown for inbox conversation
