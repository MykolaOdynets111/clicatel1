@remove_dep
@Regression
@orca_api
Feature: Departments

  Background:
    Given I login as agent of General Bank Demo
    And New departments with AutomationSecond name AutoDescriptionSecond description and second agent is created
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to support message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2276")
  Scenario: CD:: Dashboard:: Departments:: Transfer_Chat:: Verify if possible to transfer chat to department
    Then Agent has new conversation request from orca user
    Given I login as second agent of General Bank Demo
    When First Agent click on new conversation request from orca
    And Agent transfers chat to AutomationSecond department
    Then Second agent has new conversation request from orca user