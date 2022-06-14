@no_widget
Feature: Chat2Pay ::Chat2PayPaymentConfirmation

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118473")
  Scenario: ChatDesk:: Agent Initiate to Live Chat
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent click on Live Customers on dashboard
    Then Admin should see WhatsApp chart in Live Chats by Channel
    Then C2P is integrated with Chat Desk for the tenant.
    And click on C2P Payment Option
    And  Click on Chat to Pay popup option button chat section
    Then Click on Send Chat to Pay 1000 and Link 1223 OrderNumber
    Then Agent click on "Cancel Payment" request
    Then Verify Orca returns PAYMENT_LINK_CANCELLED response during 40 seconds
    When Send //end message by ORCA
    Then the chat is closed