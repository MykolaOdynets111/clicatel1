@no_widget
Feature: CD :: Agent Desk :: Closed Chat :: Chat2Pay :: Verify that the customer can close a chat using //end or //stop keywords, when payment is not concluded

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-118471")
  Scenario: ChatDesk:: Agent Initiate to Live Chat
    Given Agent click on Live Customers on dashboard
    Then Admin should see WhatsApp chart in Live Chats by Channel
    Then C2P is integrated with Chat Desk for the tenant.
    And click on C2P Payment Option
    And  Click on Chat to Pay popup option button chat section
    Then Click on Send Chat to Pay 1000 and Link 1223 OrderNumber
    And Wait for 20 second
    Then Agent click on "Cancel Payment" request
    Then Verify Orca returns PAYMENT_LINK_CANCELLED response during 40 seconds
    When Send //end message by ORCA
    Then the chat is closed