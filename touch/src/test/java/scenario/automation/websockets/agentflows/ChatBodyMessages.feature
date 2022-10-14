@Regression
@no_widget
Feature: Actions on chat body in agent desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1785")
  Scenario: CD:: SMS:: Link:: Verify if agent is able to open the link in the new tab
    Given I login as agent of Automation
    When Setup ORCA sms integration for Automation tenant
    And Send to agent message by ORCA
    And Agent has new conversation request from sms user
    And Agent click on new conversation request from sms
    And Send https://www.google.com/ message by ORCA
    And Conversation area becomes active with https://www.google.com/ user's message
    And Agent clicks the link message https://www.google.com/
    And Agent switches to opened ChatDesk page
    Then Agent checks the link https://www.google.com/ is opened in the new tab