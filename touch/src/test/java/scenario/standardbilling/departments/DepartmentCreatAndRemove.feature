@no_widget
@remove_dep
Feature: Departments

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2459")
  @Regression
  Scenario: Create and Remove department
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given Admin select TOUCH in left menu and Dashboard in submenu
    Then Admin click on Departments Management button
    And Departments Management page should be shown
    Then Agent create Department with AutoDepartmentName name Auto Description description and SB Main Agent
    And Remove Department with AutoDepartmentName name and Auto Description description