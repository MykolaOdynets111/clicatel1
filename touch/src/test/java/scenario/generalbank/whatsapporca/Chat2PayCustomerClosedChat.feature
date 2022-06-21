@no_widget
Feature: Chat2Pay ::Chat2PayCustomerClosedChat

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118471")
  Scenario: CD :: Agent Desk :: Closed Chat :: Chat2Pay :: Verify that the customer can close a chat using //end or //stop keywords, when payment is not concluded
    Given I login as agent of General Bank Demo
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with Chat to Support user's message
    Then C2P is integrated with Chat Desk for the tenant.
    And click on C2P Payment extension Option
    And Agent Click on Chat to Pay popup option button from chat section
    And Agent Click on Send Chat to Pay 1321 and Link 122323 OrderNumber
    Then Agent click on "Cancel Payment" request
    Then Verify Orca returns PAYMENT_LINK_CANCELLED response during 40 seconds
    And Send //end message by ORCA