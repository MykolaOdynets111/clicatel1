@no_widget
@remove_dep
Feature: Departments: Verify if not possible to create two departments with the same name

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-14884")
  Scenario: Duplicate verification
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given Admin select TOUCH in left menu and Departments Management in submenu
    And Departments Management page should be shown
    Then Create Department with AutoDepartmentName name Auto Description description and Standard Billing Agent
    And Create Department with AutoDepartmentName name Auto Description description and Standard Billing Agent
    Then Verify Department Duplication Alert message displayed