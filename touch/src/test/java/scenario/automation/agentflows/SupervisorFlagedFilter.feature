@no_widget
@dot_control
Feature: Supervisor desk

  Background:
    Given Create .Control integration for Automation and adapter: fbmsg
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  Scenario: Supervisor desk :: Verify if supervisor can filter chats by "Flagged Only" Conversation status
    Given I login as agent of Automation
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    When Agent click 'Flag chat' button
    Then Agent sees 'flag' icon in this chat
    When Agent switches to opened Portal page
    When I select Touch in left menu and Supervisor Desk in submenu
    And Supervisor put a check mark on "Flagged Only" and click "Apply Filters" button
    Then dotcontrol request is shown on Supervisor Desk Live page



