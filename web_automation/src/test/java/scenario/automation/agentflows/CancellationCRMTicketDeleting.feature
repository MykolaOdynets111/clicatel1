@agent_feedback
Feature: Verification cancellation of CRM ticket deleting

  Scenario: Cancellation of CRM ticket deleting
    Given User select Automation tenant
    And Click chat icon
    Given CRM ticket with URL is created
    Given I login as agent of Automation
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation
    Then New CRM ticket is shown
    When Agent click 'Delete' button for CRM ticket
    Then Confirmation deleting popup is shown
    When Agent click 'Cancel' button' in CRM deleting popup
    Then New CRM ticket is shown



