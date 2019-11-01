@no_widget
@remove_dep
Feature: Add tests on department creating

  Scenario: Create and Remove department
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given Admin select TOUCH in left menu and Departments Management in submenu
    And Departments Management page should be shown
    Then Create Department with AutoDepartmentName name Auto Description description and Standard Billing Agent
    And Remove Department with AutoDepartmentName name and Auto Description description