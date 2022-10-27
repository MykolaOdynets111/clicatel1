@support_hours
@remove_dep
@Regression
@orca_api
Feature: Departments

  Background:
    Given I login as agent of Standard Billing
    And Setup ORCA whatsapp integration for Standard Billing tenant
    And New departments with AutomationSecond name AutoDescriptionSecond description and second agent is created
    When Send connect to support message by ORCA
    Then Agent has new ticket request from orca user

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2453")
  Scenario: Departments: Verify if possible to transfer overnight ticket to department

    Given I login as second agent of Standard Billing
    And Set agent support hours with day shift
    When First Agent click on new conversation request from orca
    And Agent transfers chat to AutomationSecond department
    When Second agent select "Tickets" left menu option
    And User select Assigned ticket type
    Then Second agent has new ticket request from orca user