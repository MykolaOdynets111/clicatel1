@orca_api
@Regression
Feature: Agent should see active chat after page was refreshed

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2881")
  Scenario: CD:: Agent_Desk-Live_Chat:: Verify user chat remains in chatdesk after refresh
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent refreshes the page
    Then Agent has new conversation request
    When Agent click on new conversation request from orca
    And Conversation area becomes active with connect to Support user's message
