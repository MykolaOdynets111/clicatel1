@no_widget
@dot_control
@start_server
Feature: Creating .Control integration and sending messages from user with context

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4731")
  Scenario: Sending user's data in init .Control call with context
    Given Create .Control integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Send parameterized init call with context correct response is returned
    And Send chat to agent message for .Control from existed client
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    Then Correct dotcontrol client details from Init context are shown