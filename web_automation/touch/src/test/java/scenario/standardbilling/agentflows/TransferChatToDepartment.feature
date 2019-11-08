@no_widget
@remove_dep
Feature: Departments: Verify if possible to transfer chat to department

  @TestCaseId("https://jira.clickatell.com/browse/TPLAT-4561")
  Scenario: Verify correct agents numbers in created department
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    And New departments with Automation name AutoDescription description and main agent is created
    And New departments with AutomationSecond name AutoDescriptionSecond description and second agent is created
    Given Admin select TOUCH in left menu and Departments Management in submenu

