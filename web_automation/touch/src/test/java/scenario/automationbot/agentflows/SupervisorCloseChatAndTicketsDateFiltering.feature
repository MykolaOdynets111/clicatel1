@no_widget
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-57105")
  Scenario: Supervisor inbox :: Verify if Supervisor can filter closed chat and tickets by date range filtering
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    And Agent filter by 1 days ago start date and today's end date
    And Agent load all filtered closed chats
    Then Verify closed chats dates are fitted by filter
    And Agent select "Tickets" left menu option
    And Agent filter by 1 days ago start date and today's end date
    And Agent load all filtered tickets
    Then Verify tickets dates are fitted by filter