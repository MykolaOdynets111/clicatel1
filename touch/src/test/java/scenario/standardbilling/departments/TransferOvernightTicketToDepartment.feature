@remove_dep
@agent_support_hours
Feature: Departments

  Background:
    Given I login as agent of Standard Billing
    And New departments with AutomationSecond name AutoDescriptionSecond description and second agent is created
    Given Agent select "Tickets" left menu option
    And Set agent support hours with day shift
    Given Setup ORCA whatsapp integration for Standard Billing tenant
    When Send connect to support message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-14876")
  Scenario: Departments: Verify if possible to transfer overnight ticket to department
    Then Agent has new ticket request from orca user
    Given I login as second agent of Standard Billing
    When First Agent click on new conversation request from orca
    And Agent transfers chat to AutomationSecond department
    When Second agent select "Tickets" left menu option
    Then Second agent has new ticket request from orca user