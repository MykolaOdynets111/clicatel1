@no_widget
@dot_control
@start_server
Feature: END message for .Control

  Background:
    Given Create .Control integration for Standard Billing and adapter: whatsapp
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-28813")
  Scenario: //END message for .Control
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    When I launch chatdesk from portal
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    And Agent responds with welcome to User
    Then Verify dot .Control returns welcome response during 10 seconds
    When Prepare payload for sending //end message for .Control
    And Send message call
    Then Verify dot .Control returns Thank you. Chat soon! response during 10 seconds
    Then Agent should not see from user chat in agent desk from dotcontrol

