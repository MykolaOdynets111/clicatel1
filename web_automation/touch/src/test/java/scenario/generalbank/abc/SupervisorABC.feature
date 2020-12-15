@no_widget
@orca_api
Feature: Apple Business Chat :: Supervisor Desk

  Background:
    Given Setup ORCA integration for General Bank Demo tenant
    When Send chat to agent message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45498")
  Scenario: Supervisor desk:: verify that supervisor is able to check apple live chats
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Supervisor can see orca live chat with chat to agent message to agent