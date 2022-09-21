
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2810")
  @Regression
  Scenario: CD :: Supervisor Desk :: Verify that 'All tickets' is selected by default when a supervisor open ticket tab
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    Then Filter "All tickets" is selected by default
