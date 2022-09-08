@no_widget
Feature: Whatsapp ORCA :: ChatDesk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1174")
  @Regression
  Scenario: CD :: Agent Desk :: Live Chat :: Location :: Verify the end-user can share the current location on WhatsApp via Flow
    Given I login as agent of Standard Billing
    Given Setup ORCA whatsapp integration for Standard Billing tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    When User send Lviv location message to agent by ORCA
    When Agent sees Lviv Location from User