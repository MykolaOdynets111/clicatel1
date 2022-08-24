@no_widget
@dot_control
Feature: Supervisor desk

  Background:
    Given Create .Control integration for Automation and adapter: whatsapp
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned

  Scenario: Supervisor Desk : Verify if WA name or number is displayed for closed chats
    Given I login as agent of Automation
    And Send message call
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    And Agent closes chat
    When Agent switches to opened Portal page
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    Then WA chat show the name of the user
