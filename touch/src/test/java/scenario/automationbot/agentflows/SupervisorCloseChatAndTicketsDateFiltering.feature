@Regression
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2401")
  Scenario: CD:: Supervisor Desk:: Verify if Supervisor can filter closed chat and tickets by date range filtering
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    And Admin filter by 0 year 0 month and 90 days ago start date and today's end date
    Then Verify first closed chat date are fitted by filter
    And Agent click on the arrow of Chat Ended
    Then Verify first closed chat date are fitted by filter
    And Admin filter by 0 year 1 month and 0 days ago start date and today's end date
    Then Verify first closed chat date are fitted by filter
    And Agent click on the arrow of Chat Ended
    Then Verify first closed chat date are fitted by filter
    And Agent select "Tickets" left menu option
    And Admin filter by 0 year 0 month and 90 days ago start date and today's end date
    Then Verify first closed ticket date are fitted by filter
    And Agent click on the arrow of Ticket End Date
    Then Verify first closed ticket date are fitted by filter
    And Admin filter by 0 year 1 month and 0 days ago start date and today's end date
    Then Verify first closed ticket date are fitted by filter
    And Agent click on the arrow of Ticket End Date
    Then Verify first closed ticket date are fitted by filter

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1109")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1138")
  Scenario: CD :: Agent Desk :: Closed Chat :: Verify that the closed chats of only last 90 days are visible
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    Then Admin checks back button is not visible in calendar for start date filter 91 days ago in supervisor
    When Admin filter by 0 year 0 month and 90 days ago start date and today's end date
    Then Agent click on the arrow of Chat Ended
    And Verify first closed chat date are fitted by filter