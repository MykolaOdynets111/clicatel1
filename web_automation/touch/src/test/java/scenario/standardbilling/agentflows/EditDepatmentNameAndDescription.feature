@no_widget
@remove_dep
Feature: Departments : Edit department name/description

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-14859")
  Scenario: Edit department name and description
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given New departments with Automation name AutoDescription description and main agent is created
    And Admin select TOUCH in left menu and Departments Management in submenu
    Then Edit department with Automation name AutoDescription description
