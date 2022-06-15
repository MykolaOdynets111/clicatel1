@no_widget
Feature : ChatDeskC2PClosed
  @orca_api
  @adding_payment_method
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118471")
  Scenario: ChatDesk:: Agent Initiate to Live Chat
    Given I login as agent of General Bank Demo
    Then Agent click on Live Customers on dashboard
    Then Admin should see WhatsApp chart in Live Chats by Channel
    Then C2P is integrated with Chat Desk for the tenant.
    And click on C2P Payment Option
    And  Agent Click on Chat to Pay popup option button chat section
    Then Agent Click on Send Chat to Pay 1000 and Link 1223 OrderNumber
    Then Agent click on "Cancel Payment" request
    Then Verify Orca returns PAYMENT_LINK_CANCELLED response during 40 seconds
    When Send //end message by ORCA