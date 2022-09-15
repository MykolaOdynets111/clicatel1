@no_widget
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2849")
  Scenario: Supervisor desk :: Verify if Supervisor can sort with Chat Ended Ascending order
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    And Admin filter by 0 year 0 month and 1 days ago start date and today's end date
    Then Verify first closed chat date are fitted by filter
    When Agent click on the arrow of Chat Ended
    Then Chats Ended are sorted in ascending order