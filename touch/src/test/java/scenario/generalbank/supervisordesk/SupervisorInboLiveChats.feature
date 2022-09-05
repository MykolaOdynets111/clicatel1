@no_widget
Feature: Supervisor desk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-106817")
  Scenario: Supervisor Desk :: Verify if the first view on supervisor desk is ‘Chats’ tab
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    Then Verify that Chats tab is displayed first