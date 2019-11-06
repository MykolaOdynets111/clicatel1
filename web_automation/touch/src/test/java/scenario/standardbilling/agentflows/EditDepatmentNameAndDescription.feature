@no_widget
@remove_dep
Feature: Departments: Create a department with 3 agents

#  @TestCaseId("https://jira.clickatell.com/browse/TPORT-14866")
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-14859")
  Scenario: Edit department name and description
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given New departments with Automation name and AutoDescription description is created
    And Admin select TOUCH in left menu and Departments Management in submenu
    Then Edit department with Automation name AutoDescription description

