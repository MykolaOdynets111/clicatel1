@no_widget
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-57105")
  Scenario: Supervisor inbox :: Verify if Supervisor can filter closed chat and tickets by date range filtering
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    And Agent filter by 1 years ago start date and today's end date
    Then Verify first closed chat date are fitted by filter
    And Agent click on the arrow of Chat Ended
    Then Verify first closed chat date are fitted by filter
    And Agent filter by 1 months ago start date and today's end date
    Then Verify first closed chat date are fitted by filter
    And Agent click on the arrow of Chat Ended
    Then Verify first closed chat date are fitted by filter
    And Agent select "Tickets" left menu option
    And Agent filter by 1 years ago start date and today's end date
    Then Verify first closed ticket date are fitted by filter
    And Agent click on the arrow of Ticket End Date
    Then Verify first closed ticket date are fitted by filter
    And Agent filter by 1 months ago start date and today's end date
    Then Verify first closed ticket date are fitted by filter
    And Agent click on the arrow of Ticket End Date
    Then Verify first closed ticket date are fitted by filter