@agent_feedback
Feature: Verification that 5 CRM tickets are shown and sorted by created date

  @Issue("https://jira.clickatell.com/browse/TPORT-3510")
  Scenario: Check CRM tickets number and sorting
    Given User select Automation tenant
    And Click chat icon
    Given I login as agent of Automation
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    Given 6 CRM tickets are created
    And Agent click on new conversation
    Then Agent sees 6 CRM tickets
    And Tickets are correctly sorted
    When Agent deletes first ticket
    Then Tickets number is reduced to 5


