@smoke
Feature: Chat desk: Default placeholders and tips

  Verification of basic placeholders in chatdesk

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-5928")
  Scenario: Chat desk: Default placeholders and tips
    Given User select General Bank Demo tenant
    And Click chat icon
    Given I login as agent of General Bank Demo
    Then Agent sees "No chat selected" tip in conversation area
    And Agent sees "Context will be available after a chat is selected" tip in context area
    When User enter to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Agent sees "Type a message..." placeholder in input field

