@no_widget
@remove_dep
Feature: Departments

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2344")
  @Regression
  Scenario: Edit department name and description
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given Admin select TOUCH in left menu and Dashboard in submenu
    Then Admin click on Departments Management button
    And Departments Management page should be shown
    Then Agent create Department with Automation name AutoDescription description and SB Main Agent
    Then Edit department with Automation name AutoDescription description
