@egor
Feature: Supervisor inbox: Filter "All tickets" is selected by default

#  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4055")
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-38811")
  Scenario: Supervisor inbox :: Verify that 'All tickets' selected by default when a supervisor open ticket tab
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    Then Filter "All tickets" is selected by default
