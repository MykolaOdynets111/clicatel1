@agent_feedback
Feature: Verification that CRM ticket is shown in active chat

  Scenario: Check created CRM ticket shown in chatdesk
    Given User select General Bank Demo tenant
    And Click chat icon
    Given CRM ticket with URL is created
    Given I login as agent of General Bank Demo
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation
    Then Container with new CRM ticket is shown
    And Correct ticket info is shown
    When I click CRM ticket number URL
    Then Agent is redirected by CRM ticket URL

  Scenario: Check created CRM ticket with empty link
    Given User select General Bank Demo tenant
    And Click chat icon
    Given CRM ticket without URL is created
    Given I login as agent of General Bank Demo
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation
    Then Container with new CRM ticket is shown
    When I click CRM ticket number URL
    Then Agent is redirected to empty chatdesk page

