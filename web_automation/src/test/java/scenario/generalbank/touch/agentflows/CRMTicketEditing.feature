@agent_feedback
Feature: Verification CRM ticket editing

  Scenario: Cancellation of CRM ticket editing
    Given User select General Bank Demo tenant
    And Click chat icon
    Given CRM ticket with URL is created
    Given I login as agent of General Bank Demo
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation
    Then New CRM ticket is shown
    When Agent click 'Edit' button for CRM ticket
    Then 'Edit ticket' window is opened
    When I fill in the form with new CRM ticket info
    And Cancel CRM editing
    Then Correct ticket info is shown
    And CRM ticket is not updated on back end


