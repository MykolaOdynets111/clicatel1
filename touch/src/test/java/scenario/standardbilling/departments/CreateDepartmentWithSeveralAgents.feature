@no_widget
@remove_dep
@newagent
@Regression
Feature: Departments

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1177")
  Scenario: CD :: Dashboard :: Department Management :: Verify that the correct number of agents online/offline are displayed
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given Admin select TOUCH in left menu and Dashboard in submenu
    Then Admin click on Departments Management button
    And Departments Management page should be shown
    And Create Department with AutoDepartmentName name Auto Description description and with 3 agents
    Then Verify that card with AutoDepartmentName name and Auto Description description has 3 total 3 offline and 0 active agents
    Given I login as second agent of Standard Billing
    And Agent refresh current page
    And Departments Management page should be shown
    And Wait for 5 second
    Then Verify that card with AutoDepartmentName name and Auto Description description has 3 total 2 offline and 1 active agents
