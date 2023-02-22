@Regression
@no_widget
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2401")
  Scenario: CD:: Supervisor Desk:: Verify if Supervisor can filter closed chat and tickets by date range filtering
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    And Admin filter by 0 year 0 month and 90 days ago start date and 0 year 0 month and 3 days ago end date
    Then Verify first closed chat date are fitted by filter
    And Agent click on the arrow of Chat Ended
    Then Verify first closed chat date are fitted by filter
    And Admin filter by 0 year 1 month and 0 days ago start date and 0 year 0 month and 3 days ago end date
    Then Verify first closed chat date are fitted by filter
    And Agent click on the arrow of Chat Ended
    Then Verify first closed chat date are fitted by filter
    And Agent select "Tickets" left menu option
    And Agent select Closed filter on Left Panel
    And Admin filter by 0 year 0 month and 90 days ago start date and 0 year 0 month and 0 days ago end date
    Then Verify first closed ticket date are fitted by filter
    And Agent click on the arrow of Ticket End Date
    Then Verify first closed ticket date are fitted by filter
    And Admin filter by 0 year 1 month and 0 days ago start date and 0 year 0 month and 0 days ago end date
    Then Verify first closed ticket date are fitted by filter
    And Agent click on the arrow of Ticket End Date
    Then Verify first closed ticket date are fitted by filter

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1109")
    #Failing because of bug https://jira.clickatell.com/browse/CCD-13004
  Scenario: CD :: Supervisor Desk :: Closed Chat :: Verify that the closed chats of only last 90 days are visible to supervisor
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Closed" left menu option
    When Agent click on the arrow of Chat Ended
    Then The oldest visible chat is not more than 90 days old

  @TestCaseId("https://jira.clickatell.com/browse/CCD-7810")
  Scenario: CD :: Verify the verbiage of message under unassigned tickets for the tenant
    Given I login as second agent of Standard Billing
    When Second Agent select "Tickets" left menu option
    Then Verify  unassigned tickets section is empty
    And Second Agent can see the message There are no available unassigned tickets in the pool
