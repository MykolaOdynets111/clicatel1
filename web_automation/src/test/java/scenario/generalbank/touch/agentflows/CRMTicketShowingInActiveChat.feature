#Feature: Verification that CRM ticket is shown in active chat
#
#  Scenario: Check created CRM ticket shown in chatdesk
#    Given User select General Bank Demo tenant
#    And Click chat icon
#    Given I login as agent of General Bank Demo
#    When User enter chat to support into widget input field
#    Then Agent has new conversation request
#    Given CRM ticket is created
#    When Agent refreshes the page
#    And Agent click on new conversation
#
