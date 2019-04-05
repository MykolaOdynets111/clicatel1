@agent_feedback
Feature: Verification that 5 CRM tickets are shown and sorted be created date

  Scenario: Check CRM tickets number and sorting
    Given User select General Bank Demo tenant
    And Click chat icon
    Given 6 CRM tickets are created
    Given I login as agent of General Bank Demo
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation
    Then Agent sees 6 CRM tickets
    And Tickets are correctly sorted
    When Agent deletes first ticket
    Then Tickets number is reduced to 5


