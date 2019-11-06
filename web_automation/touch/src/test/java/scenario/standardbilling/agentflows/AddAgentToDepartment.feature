@no_widget
@remove_dep
@newagent
Feature: Departments : Add an agent trough "Department management"

  @TestCaseId("https://jira.clickatell.com/browse/TPLAT-4560")
  Scenario: Verify department agents adding functionality
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given Brand New Standard Billing agent is created
    And New departments with Automation name and AutoDescription description is created
    Given Admin select TOUCH in left menu and Departments Management in submenu
    And Add newly created agent to department with Automation name and AutoDescription description
    And Agent refresh current page
    Then Verify that card with Automation name and AutoDescription description has 2 total 2 offline and 0 active agents