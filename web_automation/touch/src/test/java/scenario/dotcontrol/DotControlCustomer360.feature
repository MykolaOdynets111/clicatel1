@no_widget
@dot_control
Feature: .Control customer 360

  @Issue("https://jira.clickatell.com/browse/TPLAT-4379")
  Scenario: Viewing .Control customer 360 info and editing it
    Given Create .Control integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    When Send message call
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    Then Correct dotcontrol client details are shown
    And Click 'Edit' button in Customer 360 view
    When Fill in the form with new dotcontrol customer 360 info
    And Click 'Save' button in Customer 360 view
    Then dotcontrol customer info is updated on backend
    And Correct dotcontrol client details are shown
    And New info is shown in left menu with chats
    And Customer name is updated in active chat header
