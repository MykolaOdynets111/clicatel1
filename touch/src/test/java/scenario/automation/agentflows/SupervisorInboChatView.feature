@no_widget
@dot_control
Feature: Supervisor desk

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  Scenario: Supervisor desk: : verify if correct chat type along with time stamp is shown in chat view
    @skip
    Given I login as agent of Automation
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    And Agent responds with welcome to User
    When Collect Agent chat messages
    When Agent switches to opened Portal page
    And I select Touch in left menu and Supervisor Desk in submenu
    And Verify that correct messages and timestamps are shown on Chat View
