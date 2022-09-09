@remove_dep
Feature: Departments

  Background:
    Given I login as agent of Standard Billing
    And New departments with AutomationSecond name AutoDescriptionSecond description and second agent is created
    Given Setup ORCA whatsapp integration for Standard Billing tenant
    When Send connect to support message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2276")
  @Regression
  Scenario: Departments: Verify if possible to transfer chat to department
    Then Agent has new conversation request from orca user
    Given I login as second agent of Standard Billing
    When First Agent click on new conversation request from orca
    And Agent transfers chat to AutomationSecond department
    Then Second agent has new conversation request from orca user