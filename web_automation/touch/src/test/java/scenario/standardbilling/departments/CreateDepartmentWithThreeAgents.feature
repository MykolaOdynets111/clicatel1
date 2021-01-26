@no_widget
@remove_dep
@newagent
Feature: Departments

  Background:
    Given Brand New Standard Billing agent is created

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-14856")
  Scenario: Verify correct agents numbers in created department
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given Admin select TOUCH in left menu and Departments Management in submenu
    And Departments Management page should be shown
    And Create Department with AutoDepartmentName name Auto Description description and with 3 Agents
    Then Verify that card with AutoDepartmentName name and Auto Description description has 3 total 3 offline and 0 active agents
    Given I login as second agent of Standard Billing
    And Agent refresh current page
    And Departments Management page should be shown
    And Wait for 3 second
    Then Verify that card with AutoDepartmentName name and Auto Description description has 3 total 2 offline and 1 active agents
