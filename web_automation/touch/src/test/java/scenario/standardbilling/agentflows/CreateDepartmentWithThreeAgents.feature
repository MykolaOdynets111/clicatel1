@no_widget
@remove_dep
@newagent
Feature: Departments: Create a department with 3 agents

  Scenario: Create department with 3 agents
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given Admin select TOUCH in left menu and Departments Management in submenu
    And Departments Management page should be shown
    Given Brand New Standard Billing agent is created
    Given Brand New Standard Billing agent is created
    And Create Department with AutoDepartmentName name Auto Description description and with 3 Agents
    Then Verify that 3 number of agents present in card with AutoDepartmentName name and Auto Description description
