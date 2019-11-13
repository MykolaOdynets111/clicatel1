@remove_dep
Feature: Departments: Verify if possible to transfer chat to department

  Background:
    Given User select Standard Billing tenant
    And New departments with Automation name AutoDescription description and main agent is created
    Given New departments with AutomationSecond name AutoDescriptionSecond description and second agent is created
    And I login as agent of Standard Billing
    Given Click chat icon
    And User enter connect to agent2 into widget input field

  @TestCaseId("https://jira.clickatell.com/browse/TPLAT-4561")
  Scenario: Departments: Verify if possible to transfer chat to department
    Given I login as second agent of Standard Billing
    When First Agent click on new conversation
    And Agent transfers chat to AutomationSecond department
    Then Second agent has new conversation request

