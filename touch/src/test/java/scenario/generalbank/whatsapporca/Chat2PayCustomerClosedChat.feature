@no_widget
Feature: Chat2Pay ::Chat2PayCustomerClosedChat

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105084")
    Scenario: CD :: Agent Desk :: Closed Chat :: Chat2Pay :: Verify that the customer can close a chat using //end or //stop keywords, when payment is not concluded
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When When Agent open c2p form
    And Agent fill c2p form with orderNumber 43333, price 9 and send
    When Send //end message by ORCA
    Then Agent should not see from user chat in agent desk from orca