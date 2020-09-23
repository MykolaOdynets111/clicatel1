@remove_dep
@agent_support_hours
Feature: Departments

  Background:
    Given I login as agent of Standard Billing
    And Agent select "Tickets" left menu option
    Given Set agent support hours with day shift
    And New departments with AutomationSecond name AutoDescriptionSecond description and second agent is created
    Given User select Standard Billing tenant
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-14876")
  Scenario: Departments: Verify if possible to transfer overnight ticket to department
    And User enter connect to agent2 into widget input field
    Then Agent has new conversation request
    Given I login as second agent of Standard Billing
    When First Agent click on new conversation
    And Agent transfers chat to AutomationSecond department
    When Second agent select "Tickets" left menu option
    Then Second agent has new conversation request