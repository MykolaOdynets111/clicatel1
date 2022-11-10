@no_widget
@remove_dep
@newagent
@Regression
Feature: Departments : Add an agent trough "Department management"

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2360")
  Scenario: CD :: Dashboard :: Configure :: Department Management :: Verify adding an agent through "Department management"
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    Given Admin select TOUCH in left menu and Dashboard in submenu
    Then Admin click on Departments Management button
    And Departments Management page should be shown
    And Create Department with AutoDepartmentName name Auto Description description and with 1 agents
    Then Verify that card with AutoDepartmentName name and Auto Description description has 1 total 1 offline and 0 active agents
    Then Add 1 agent to department with AutoDepartmentName name and Auto Description description
    And Departments Management page should be shown
    And Wait for 5 second
    Then Verify that card with AutoDepartmentName name and Auto Description description has 2 total 2 offline and 0 active agents