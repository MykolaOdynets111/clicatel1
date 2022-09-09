@no_widget
@remove_dep
Feature: Departments

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2836")
  @Regression
  Scenario: Verify if supervisor is able to launch department management page from dashboard view
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given Admin select TOUCH in left menu and Dashboard in submenu
    Then Admin can see 'Welcome to the Chat Desk Dashboard'
    Then Admin click on Departments Management button
    And Departments Management page should be shown

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2446")
  @Regression
  Scenario: Departments: Verify if not possible to create two departments with the same name
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given Admin select TOUCH in left menu and Dashboard in submenu
    Then Admin click on Departments Management button
    And Departments Management page should be shown
    When Agent create Department with AutoDepartmentName name Auto Description description and SB Main Agent
    Then Verify Agent cannot create department with duplicate AutoDepartmentName name