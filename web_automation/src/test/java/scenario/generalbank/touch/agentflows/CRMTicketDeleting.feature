@agent_feedback
Feature: Verification CRM ticket deleting

  Scenario: CRM ticket deleting
    Given User select General Bank Demo tenant
    And Click chat icon
    Given CRM ticket with URL is created
    Given I login as agent of General Bank Demo
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation
    Then New CRM ticket is shown
    When Agent click 'Delete' button for CRM ticket
    Then Confirmation deleting popup is shown
    When Agent click 'Delete' button' in CRM deleting popup
    Then New CRM ticket is not shown



