@no_widget
@dot_control
Feature: Supervisor see chat view with correct info

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

#  @TestCaseId("https://jira.clickatell.com/browse/TPORT-15682")
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-42295")
  Scenario: supervisor desk: : verify if correct chat type along with time stamp is shown in chat view
    Given I open portal
    And Login into portal as an admin of Automation account
    When I launch chatdesk from portal
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    And Agent responds with welcome to User
    When Collect Agent chat messages
    When Agent switches to opened Portal page
    And I select Touch in left menu and Supervisor Desk in submenu
    And Verify that New chat status correct last message and timestamp is shown on Chat View
