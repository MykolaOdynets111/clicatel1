@no_widget
@Regression
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2849")
  Scenario: CD::Supervisor desk :: Verify if Supervisor can sort with Chat Ended Ascending order
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    And Admin filter by 0 year 0 month and 1 days ago start date and 0 year 0 month and 0 days ago end date
    Then Verify first closed chat date are fitted by filter
    When Agent click on the arrow of Chat Ended
    Then Chats Ended are sorted in ascending order

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1131")
  Scenario: CD :: Supervisor Desk :: Closed Chat :: Verify calendar picker should be limited to max 90 days back in closed chats
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    Then Admin checks value of start date filter is empty for 0 year 0 month and 91 days ago
    And Admin checks back button is not visible in calendar for start date filter 91 days ago in supervisor
    Then Admin checks back button is visible in calendar for end date filter 90 days ago in supervisor