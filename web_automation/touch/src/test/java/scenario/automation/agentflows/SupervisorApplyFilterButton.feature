@dot_control
Feature: Supervisor desk

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call
    Given User select Automation tenant
    And Click chat icon

  Scenario: Supervisor inbox :: Verify if "apply filters" button work
    Given I login as agent of Automation
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    Then Agent has new conversation request from dotcontrol user
    When Agent switches to opened Portal page
    And I select Touch in left menu and Supervisor Desk in submenu
    Then Supervisor Desk Live has new conversation dotcontrol request
    Then Agent has new conversation request
    When Agent select "Webchat" in Chanel container and click "Apply filters" button
    Then Supervisor Desk Live dos not have conversation dotcontrol request
    And Agent has new conversation request
    Then Verify that only "widget" chats are shown
