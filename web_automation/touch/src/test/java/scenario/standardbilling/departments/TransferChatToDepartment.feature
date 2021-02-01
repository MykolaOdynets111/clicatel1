@remove_dep
Feature: Departments

  Background:
    Given I login as agent of Standard Billing
    And New departments with AutomationSecond name AutoDescriptionSecond description and second agent is created
    Given User select Standard Billing tenant
    And Click chat icon
    Given User enter connect to agent into widget input field
    And Agent has new conversation request

  @TestCaseId("https://jira.clickatell.com/browse/TPLAT-4561")
  Scenario: Departments: Verify if possible to transfer chat to department
    Given I login as second agent of Standard Billing
    When First Agent click on new conversation
    And Agent transfers chat to AutomationSecond department
    Then Second agent has new conversation request

