@no_widget
@dot_control
Feature: Supervisor able to check live chats

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  Scenario: Supervisor desk :: verify that supervisor able to check live chats
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Verify that live chat is displayed with chat to agent message to agent
    When Agent switches to opened Portal page
    When I select Touch in left menu and Agent Desk in submenu
    Then Agent has new conversation request from dotcontrol user

